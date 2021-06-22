
const asMoney = (rawMoney) => rawMoney.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
const zerofill = (x, n) => ('0'.repeat(n + 1) + x).slice(-n);
const reportDate = (d) => `${zerofill(d.getDate(), 2)}/${zerofill(d.getMonth() + 1, 2)}/${d.getFullYear()} ${d.getHours()}:${d.getMinutes()}`;

module.exports = {
    asMoney,
    zerofill,
    reportDate
};
