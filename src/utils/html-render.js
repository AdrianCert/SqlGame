// match start xml/html node
const re_context_start = /<.+>/gm;
// match end xml/html node
const re_context_end = /<\s*\/([^/]*)>/gm;
// match variable block
const re_tpl_variable = /{{\s*([\w_]+)\s*}}/gm;
// match control node
const re_tpl_control = /<\$\s*(.+)\s*\$>/gm;
// const re_token = ;
const re_node = /<.*>.+<\/.*>/gm;
const re_node_simple = /<[^$].*>.+<\/.*>/gm;
const re_node_non_controll = /<\s*[^$/].*>/gm;
const re_node_name_end = /<\s*\/(.*)>/gm;
const re_node_name_start = /^<(\w+)/gm;
const re_node_name = /\w+/gm;

const buildpartials = require('./html-parts');

function HtmlRender(document, dic) {
    return applyStrategyParse(document, dic);
}

const val = {
    "strategy" : "__strategy"
}

const re = {
    "token" : /<.*>.+<\/.*>|<[^<>]+>/gm,
    "node" : {
        "start" : /<\s*[^%/].*>/gm,
        "end" : /<\s*\/([^/]*)>/gm,
        "name" : /\w+/gm
    },
    "control" : {
        "node" : /<%\s*[^<>%]+\s+%>/gm,
        "variable" : /{{\s*([\w_]+)\s*}}/gm,
        "symbol" : /\w[\w\d_]*/gm
    }
}

function applyStrategyParse(document, dic) {
    let strategy = [
        htmlProcessingIteretingWithParts,
        htmlProcessingItereting,
        htmlNotProcessing
    ];

    let choice = val.strategy in dic ? dic[val.strategy] : 0;
    choice = choice >= strategy.length ? 0 : choice;

    return strategy[choice](document, dic);

}

function htmlProcessingIteretingWithParts(document, dic) {
    dic = buildpartials(dic);
    return htmlProcessingItereting(document, dic)
}

function htmlNotProcessing(document, dic) {
    return Buffer.from(document);
}

function htmlProcessingItereting(document, dic) {
    return Buffer.from(htmlProcessingIteretingString(document, dic));
}

function htmlProcessingIteretingString(document, dic, indexes = []) {
    return processNodeByNode( {
        "doc" : document.toString(),
        "dic" : dic,
        "indexes" : indexes
    });
}

function processNodeByNode(pack) {
    let node; 
    pack.rstack = [];
    re.token.lastIndex = 0;
    while( (node = re.token.exec(pack.doc))) {
        pack.indexes.push(re.token.lastIndex);
        processNode(pack, node[0]);
        re.token.lastIndex = pack.indexes.pop();
    }
    return pack.rstack.reduce((a, c) => a + c, '')
}

function processNode(pack, node) {
    if( node.match(re.node.start)) {
        pack.rstack.push(nodeReplaceVariable(node, pack.dic));
    } else
    if(node.match(re.control.node)) {
        nodeControlExecute(pack, node);
    } else
    if( node.match(re.node.end)) {
        let node_name = node.match(re.node.name)[0];
        let node_childs = [node];

        while( pack.rstack.length) {
            let child = pack.rstack.pop();
            let child_name = child.match(re.node.name)[0];
            node_childs.push(child);

            if( child_name && child_name == node_name) {
                let current_node = '';
                while(node_childs.length) current_node += node_childs.pop();
                pack.rstack.push(current_node);
                break;
            }
        }
    }
}

function nodeControlExecute(pack, node) {
    let m = node.match(re.control.symbol);
    if(m === null) return;
    switch(m[0]) {
        case 'partial' :
            if(!('parts' in pack.dic)) break;
            if(m[1] in pack.dic.parts) {
                pack.rstack.push(htmlProcessingIteretingString(pack.dic.parts[m[1]], pack.dic, pack.indexes));
            }
            break;
        case 'id':
            break;
        case 'endif':
            break;
    }
}

function nodeReplaceVariable(node, dic) {
    let variable, result = node;
    while( (variable = re.control.variable.exec(node))) {
        result = result.replace(variable[0], dic[variable[1]]);
    }
    return result;
}

module.exports = { HtmlRender }
