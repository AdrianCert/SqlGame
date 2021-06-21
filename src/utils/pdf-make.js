const path = require('path');
const pdfMakePrinter = require('pdfmake');
const { clearLine } = require('readline');

const utils = {
    makeCell: (content, rowIndex = -1, options = {}) => {
        return Object.assign({ text: content, fillColor: rowIndex % 2 ? 'white' : '#e8e8e8' }, options);
    },

    thl: (content, rowIndex = -1, options = {}) => {
        return utils.makeCell(content, rowIndex, Object.assign({ bold: true, alignment: 'left', fontSize: 9 }, options));
    },

    thr: (content, rowIndex = -1, options = {}) => {
        return utils.makeCell(content, rowIndex, Object.assign({ bold: true, alignment: 'right', fontSize: 9 }, options));
    },

    tdl: (content, rowIndex = -1, options = {}) => {
        return utils.makeCell(content, rowIndex, Object.assign({ bold: false, alignment: 'left', fontSize: 9 }, options));
    },

    tdr: (content, rowIndex = -1, options = {}) => {
        return utils.makeCell(content, rowIndex, Object.assign({ bold: false, alignment: 'right', fontSize: 9 }, options));
    },

    truncateContent: (content, maxLength = 17) => {
        return ''.concat(content.slice(0, maxLength), content.length > maxLength ? '…' : '');
    },

    asMoney : (rawMoney) => rawMoney.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,"),

    zerofill: (x, n) => ('0'.repeat(n + 1) + x).slice(-n),

    reportDate : (d) => `${utils.zerofill(d.getDate(), 2)}/${utils.zerofill(d.getMonth() + 1, 2)}/${d.getFullYear()} ${d.getHours()}:${d.getMinutes()}`
}

function createPdfBinary(pdfDoc, callback) {

    let fontDescriptors = {
        Roboto: {
            normal: path.join(__dirname, '..', 'resources', '/fonts/Roboto-Regular.ttf'),
            bold: path.join(__dirname, '..', 'resources', '/fonts/Roboto-Medium.ttf'),
            italics: path.join(__dirname, '..', 'resources', '/fonts/Roboto-Italic.ttf'),
            bolditalics: path.join(__dirname, '..', 'resources', '/fonts/Roboto-MediumItalic.ttf')
        }
    };

    let printer = new pdfMakePrinter(fontDescriptors);
    let doc = printer.createPdfKitDocument(pdfDoc);

    let chunks = [];
    doc.on('data', function (chunk) {
        chunks.push(chunk);
    });
    doc.on('end', function () {
        callback( Buffer.concat(chunks));
    });
    doc.end();

}

function createPdfReportDocumentDefinition(reportDate, title, subtitle, ...parts) {
    const baseDoc = {
        pageSize: 'A4',

        content: [
            {
                columns: [
                    { text: title, style: 'title', width: '*' },
                    { text: reportDate, style: 'titleDate', width: '160' },
                ]
            },
            { text: `${subtitle}\n\n`, style: 'titleSub' },
        ],

        styles: {
            title: {
                fontSize: 24
            },
            titleSub: {
                fontSize: 18
            },
            titleDate: {
                fontSize: 14,
                alignment: 'right',
                bold: true
            }
        },

        footer: (currentPage, pageCount) => {
            return {
                text: `${reportDate} : Page ${currentPage.toString()} of ${pageCount.toString()}`,
                alignment: 'center',
                fontSize: 7
            }
        }
    };

    let doc = JSON.parse(JSON.stringify(baseDoc));
    doc.footer = baseDoc.footer;
    doc.content.push(...parts);
    return doc;
}


function generatePdfReportClasament(data) {

    const makeTable = (dataRows) => {
        const body = [
            [
                utils.thl(' ', -1, {colSpan: 2}),
                utils.thl(' '),
                utils.thr('Rank'),
                utils.thl('Email'),
                utils.thr('Coins')
            ],
            []
        ];

        let totalCoins = 0;

        dataRows.forEach((row, index) => {
            const tableRow = [];
            tableRow.push(utils.tdl(row['ID'], index));
            tableRow.push(utils.tdl(utils.truncateContent(row['NAME'], 30), index));
            tableRow.push(utils.tdr(`#${index + 1}`, index));
            tableRow.push(utils.tdl(utils.truncateContent(row['EMAIL'], 30), index));
            tableRow.push(utils.tdr(utils.asMoney(row['COINS']), index));
            body.push(tableRow);

            totalCoins += row['COINS'];
        });

        body[1] = [
            utils.tdl(`Total for ${dataRows.length} members`, -1, {colSpan: 3, fillColor: 'black', color: 'white'}),
            utils.tdl(' '),
            utils.tdr(' '),
            utils.tdr(utils.asMoney(totalCoins), -1, {colSpan: 2, fillColor: 'black', color: 'white'}),
            utils.tdr(' ')
        ];
        return body;
    }

    let tableData = {
        table: {
            headerRows: 1,
            widths: [40, '*', 40, 150, 70],

            body: makeTable(data),
        }
    };
    
    return createPdfReportDocumentDefinition(utils.reportDate(new Date()), 'SQL Game - Classment', 'Top users', tableData);

}

function generatePdfReportHistory(data) {

}

module.exports = { 
    createPdfBinary,
    createPdfReportDocumentDefinition,
    generatePdfReportClasament,
    generatePdfReportHistory
};