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
    this.dic = dic;
    this.doc = document;
    HtmlRender.prototype.process.call(this, this);
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

HtmlRender.prototype.process = (self) => {
    self.rstack = [];
    let node;
    while( (node = re.token.exec(self.doc))) {
        node = node[0];

        if( node.match(re.node.start)) {
            HtmlRender.prototype.place_var.call(this, self, node);
            self.rstack.push(node);
        }
        if( node.match(re.node.end)) {
            let node_name = node.match(re.node.name)[0];
            let node_childs = [node];

            while( self.rstack.length) {
                let child = self.rstack.pop();
                let child_name = child.match(re.node.name)[0];
                node_childs.push(child);

                if( child_name && child_name == node_name) {
                    let current_node = '';
                    while(node_childs.length) current_node += node_childs.pop();
                    self.rstack.push(current_node);
                    break;
                }
            }
        }
    }

    self.document = self.rstack.reduce((a, c) => a + c, '');

};

HtmlRender.prototype.place_var = (self, node, dic = null) => {
    dic = dic === null ? self.dic : dic;
    let variables;
    if( (variables = node.match(re.control.variable))) {
        variables.forEach(variable => {
            let key = variable.match(re.node.name)[0];
            node = node.replace(variable, dic[key]);
        });
    }
};

module.exports = { HtmlRender }
