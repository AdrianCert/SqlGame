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

function HtmlRender(document, dic) {
    return applyStrategyParse(document, dic);
}

const re = {
    "token" : /<.*>.+<\/.*>|<[^<>]+>/gm,
    "node" : {
        "start" : /<\s*[^$/].*>/gm,
        "end" : /<\s*\/([^/]*)>/gm,
        "name" : /\w+/gm
    },
    "control" : {
        "variable" : /{{\s*([\w_]+)\s*}}/gm
    }
}

function applyStrategyParse(document, dic) {
    let strategy = [
        htmlProcessingItereting,
        htmlNotProcessing
    ];

    let choice = 0;

    return strategy[choice](document, dic);

}

function htmlNotProcessing(document, dic) {
    return Buffer.from(document);
}

function htmlProcessingItereting(document, dic) {
    return Buffer.from(processNodeByNode( {
        "doc" : document.toString(),
        "dic" : dic
    }));
}

function processNodeByNode(pack) {
    let node; 
    pack.rstack = [];
    while( (node = re.token.exec(pack.doc))) {
        processNode(pack, node[0]);
    }
    return pack.rstack.reduce((a, c) => a + c, '')
}

function processNode(pack, node) {
    if( node.match(re.node.start)) {
        pack.rstack.push(nodeReplaceVariable(node, pack.dic));
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

function nodeReplaceVariable(node, dic) {
    let variable, result = node;
    while( (variable = re.control.variable.exec(node))) {
        result = result.replace(variable[0], dic[variable[1]]);
    }
    return result;
}

module.exports = { HtmlRender }
