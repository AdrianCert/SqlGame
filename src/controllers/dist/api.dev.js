"use strict";

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance"); }

function _iterableToArray(iter) { if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === "[object Arguments]") return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } }

var Router = require('./../routing');

var api = require('./../binder/api');

var queryApi = require('./../binder/queryHandler');

var _require = require('./../utils/pdf-make'),
    createPdfBinary = _require.createPdfBinary,
    generatePdfReportClasament = _require.generatePdfReportClasament;

var locations = Object.keys(api);
var queries = {
  "classment": "select u.id as id, u.name || ' ' || u.surname as name , u.mail as email, w.balancing as coins\n    from usertable u\n    join userwallet uw on u.id = uw.user_id\n    join wallet w on uw.wallet_id = w.id\n    order by w.balancing desc\n    ".split(/\s+/).join(' '),
  "history": "w"
};

function homeView(req, res) {
  JsonRespone(res);
}

function whoIAm(req, res) {
  var intern,
      data,
      _args = arguments;
  return regeneratorRuntime.async(function whoIAm$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          intern = _args.length > 2 && _args[2] !== undefined ? _args[2] : false;

          if (!(req.headers.hasOwnProperty('auth') && req.headers.auth.is_authenticated)) {
            _context.next = 7;
            break;
          }

          _context.next = 4;
          return regeneratorRuntime.awrap(api.user.get(req.headers.auth.info.user));

        case 4:
          _context.t0 = _context.sent;
          _context.next = 8;
          break;

        case 7:
          _context.t0 = {};

        case 8:
          data = _context.t0;
          return _context.abrupt("return", intern ? data : JsonRespone(res, data));

        case 10:
        case "end":
          return _context.stop();
      }
    }
  });
}

function getQuestionCredidentials(id) {
  var nfo;
  return regeneratorRuntime.async(function getQuestionCredidentials$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          nfo = {
            "sgbd": "ORACLE",
            "user": "STUDENT",
            "pass": "STUDENT"
          };

          if (id === 13456433) {
            console.log("pentru gestiunea mai multor scheme bazelor de date");
          }

          return _context2.abrupt("return", nfo);

        case 3:
        case "end":
          return _context2.stop();
      }
    }
  });
}

function getInternBdCreddidentials() {
  return {
    "sgbd": "ORACLE",
    "user": "TW",
    "pass": "TW"
  };
}

function checkQuestionAnswer(req, res) {
  var body;
  return regeneratorRuntime.async(function checkQuestionAnswer$(_context4) {
    while (1) {
      switch (_context4.prev = _context4.next) {
        case 0:
          if (!(req.method !== 'POST')) {
            _context4.next = 4;
            break;
          }

          res.writeHead(405);
          res.end();
          return _context4.abrupt("return");

        case 4:
          body = [];
          req.on('error', function (err) {
            console.error(err);
          }).on('data', function (chunk) {
            body.push(chunk);
          }).on('end', function _callee() {
            var qid, question, nfo, anwser;
            return regeneratorRuntime.async(function _callee$(_context3) {
              while (1) {
                switch (_context3.prev = _context3.next) {
                  case 0:
                    body = Buffer.concat(body).toString();
                    qid = /\/api\/querry\/check\/(\d+)/gm.exec(req.url)[1];
                    _context3.next = 4;
                    return regeneratorRuntime.awrap(api.question.get(qid));

                  case 4:
                    question = _context3.sent;
                    _context3.next = 7;
                    return regeneratorRuntime.awrap(getQuestionCredidentials(qid));

                  case 7:
                    nfo = _context3.sent;
                    _context3.next = 10;
                    return regeneratorRuntime.awrap(queryApi.verificate(body, question.solution, nfo.sgbd, nfo.user, nfo.pass));

                  case 10:
                    anwser = _context3.sent;

                    if (anwser.accepted) {// make history
                      // make payment
                      // make update balance
                    }

                    _context3.next = 14;
                    return regeneratorRuntime.awrap(api.histo);

                  case 14:
                    return _context3.abrupt("return", JsonRespone(res, anwser));

                  case 15:
                  case "end":
                    return _context3.stop();
                }
              }
            });
          });

        case 6:
        case "end":
          return _context4.stop();
      }
    }
  });
}

function downloadCSVResult(req, res) {
  var body;
  return regeneratorRuntime.async(function downloadCSVResult$(_context6) {
    while (1) {
      switch (_context6.prev = _context6.next) {
        case 0:
          if (!(req.method !== 'POST')) {
            _context6.next = 4;
            break;
          }

          res.writeHead(405);
          res.end();
          return _context6.abrupt("return");

        case 4:
          body = [];
          req.on('error', function (err) {
            console.error(err);
          }).on('data', function (chunk) {
            body.push(chunk);
          }).on('end', function _callee2() {
            var jbody, qid, nfo, data, exportData, buff;
            return regeneratorRuntime.async(function _callee2$(_context5) {
              while (1) {
                switch (_context5.prev = _context5.next) {
                  case 0:
                    body = Buffer.concat(body).toString();
                    jbody = JSON.parse(body);
                    qid = 0;
                    _context5.next = 5;
                    return regeneratorRuntime.awrap(getQuestionCredidentials(qid));

                  case 5:
                    nfo = _context5.sent;
                    _context5.next = 8;
                    return regeneratorRuntime.awrap(queryApi.query(jbody.querry, nfo.sgbd, nfo.user, nfo.pass).then(function (r) {
                      return r.error ? [] : r.entity;
                    })["catch"](function () {
                      return [];
                    }));

                  case 8:
                    data = _context5.sent;
                    exportData = "";

                    if (data.length > 0) {
                      // building csv response
                      // https://datatracker.ietf.org/doc/html/rfc4180
                      buff = [];
                      buff.push.apply(buff, _toConsumableArray(Object.keys(data[0]).reduce(function (a, c) {
                        return "".concat(a, ",").concat(c);
                      })));
                      data.forEach(function (r) {
                        return buff.push('\n', Object.values(r).reduce(function (a, c) {
                          return "".concat(a, ",").concat(c);
                        }));
                      });
                      exportData = buff.reduce(function (a, c) {
                        return "".concat(a).concat(c);
                      });
                    }

                    res.setHeader("Content-Type", "text/csv");
                    res.setHeader("Content-Disposition", "attachment; filename=interogration_".concat(Math.random().toString(30).substring(2), ".csv"));
                    res.writeHead(200);
                    res.end(exportData);

                  case 15:
                  case "end":
                    return _context5.stop();
                }
              }
            });
          });

        case 6:
        case "end":
          return _context6.stop();
      }
    }
  });
}

function downloadPdfClasament(req, res) {
  var body;
  return regeneratorRuntime.async(function downloadPdfClasament$(_context8) {
    while (1) {
      switch (_context8.prev = _context8.next) {
        case 0:
          if (!(req.method !== 'POST')) {
            _context8.next = 4;
            break;
          }

          res.writeHead(405);
          res.end();
          return _context8.abrupt("return");

        case 4:
          body = [];
          req.on('error', function (err) {
            console.error(err);
          }).on('data', function (chunk) {
            body.push(chunk);
          }).on('end', function _callee3() {
            var nfo, data;
            return regeneratorRuntime.async(function _callee3$(_context7) {
              while (1) {
                switch (_context7.prev = _context7.next) {
                  case 0:
                    body = Buffer.concat(body).toString();
                    nfo = getInternBdCreddidentials();
                    _context7.next = 4;
                    return regeneratorRuntime.awrap(queryApi.query(queries.classment, nfo.sgbd, nfo.user, nfo.pass).then(function (r) {
                      return r.error ? [] : r.entity;
                    })["catch"](function () {
                      return [];
                    }));

                  case 4:
                    data = _context7.sent;
                    createPdfBinary(generatePdfReportClasament(data), function (binary) {
                      res.setHeader("Content-Type", "application/pdf");
                      res.setHeader("Content-Disposition", "attachment; filename=clasament_".concat(Math.random().toString(30).substring(2), ".pdf"));
                      res.writeHead(200);
                      res.end(binary);
                    }, function (e) {
                      return res.end('ERROR:' + e);
                    });

                  case 6:
                  case "end":
                    return _context7.stop();
                }
              }
            });
          });

        case 6:
        case "end":
          return _context8.stop();
      }
    }
  });
}

function downloadPdfHistory(req, res) {
  var body;
  return regeneratorRuntime.async(function downloadPdfHistory$(_context10) {
    while (1) {
      switch (_context10.prev = _context10.next) {
        case 0:
          if (!(req.method !== 'POST')) {
            _context10.next = 4;
            break;
          }

          res.writeHead(405);
          res.end();
          return _context10.abrupt("return");

        case 4:
          body = [];
          req.on('error', function (err) {
            console.error(err);
          }).on('data', function (chunk) {
            body.push(chunk);
          }).on('end', function _callee4() {
            var jbody, qid, nfo, data, exportData, buff;
            return regeneratorRuntime.async(function _callee4$(_context9) {
              while (1) {
                switch (_context9.prev = _context9.next) {
                  case 0:
                    body = Buffer.concat(body).toString();
                    jbody = JSON.parse(body);
                    qid = 0;
                    _context9.next = 5;
                    return regeneratorRuntime.awrap(getQuestionCredidentials(qid));

                  case 5:
                    nfo = _context9.sent;
                    _context9.next = 8;
                    return regeneratorRuntime.awrap(queryApi.query(jbody.querry, nfo.sgbd, nfo.user, nfo.pass).then(function (r) {
                      return r.error ? [] : r.entity;
                    })["catch"](function () {
                      return [];
                    }));

                  case 8:
                    data = _context9.sent;
                    exportData = "";

                    if (data.length > 0) {
                      // building csv response
                      // https://datatracker.ietf.org/doc/html/rfc4180
                      buff = [];
                      buff.push.apply(buff, _toConsumableArray(Object.keys(data[0]).reduce(function (a, c) {
                        return "".concat(a, ",").concat(c);
                      })));
                      data.forEach(function (r) {
                        return buff.push('\n', Object.values(r).reduce(function (a, c) {
                          return "".concat(a, ",").concat(c);
                        }));
                      });
                      exportData = buff.reduce(function (a, c) {
                        return "".concat(a).concat(c);
                      });
                    }

                    res.setHeader("Content-Type", "text/csv");
                    res.setHeader("Content-Disposition", "attachment; filename=interogration_".concat(Math.random().toString(30).substring(2), ".csv"));
                    res.writeHead(200);
                    res.end(exportData);

                  case 15:
                  case "end":
                    return _context9.stop();
                }
              }
            });
          });

        case 6:
        case "end":
          return _context10.stop();
      }
    }
  });
}

function apiController(req, res) {
  var nrout;
  return regeneratorRuntime.async(function apiController$(_context11) {
    while (1) {
      switch (_context11.prev = _context11.next) {
        case 0:
          nrout = /\/api\/(\w+)\/(.*)/gm.exec(req.url);

          if (!(nrout !== null && locations.includes(nrout[1]))) {
            _context11.next = 3;
            break;
          }

          return _context11.abrupt("return", serveApi(req, res, nrout));

        case 3:
          return _context11.abrupt("return", new Router().path(/\/api\/$/gm, homeView).path(/\/api\/whoIAm$/gm, whoIAm).path("/api/querry/check/", checkQuestionAnswer).path("/api/querry/getcsv/", downloadCSVResult).path("/api/pdf/top/", downloadPdfClasament).path("/api/pdf/history/", downloadPdfHistory).route(req, res));

        case 4:
        case "end":
          return _context11.stop();
      }
    }
  });
}

function JsonRespone(stream, data) {
  stream.setHeader("Content-Type", "application/json");
  stream.writeHead(200);
  stream.end(JSON.stringify(data, null, 2));
}

function serveApi(req, res, unit) {
  var body = [];
  req.on('error', function (err) {
    console.error(err);
  }).on('data', function (chunk) {
    body.push(chunk);
  }).on('end', function _callee5() {
    var cttr;
    return regeneratorRuntime.async(function _callee5$(_context12) {
      while (1) {
        switch (_context12.prev = _context12.next) {
          case 0:
            body = Buffer.concat(body).toString();
            cttr = api[unit[1]];

            if (!(unit[2].length === 0)) {
              _context12.next = 17;
              break;
            }

            if (!(req.method === 'GET')) {
              _context12.next = 10;
              break;
            }

            _context12.t0 = JsonRespone;
            _context12.t1 = res;
            _context12.next = 8;
            return regeneratorRuntime.awrap(cttr.getAll());

          case 8:
            _context12.t2 = _context12.sent;
            return _context12.abrupt("return", (0, _context12.t0)(_context12.t1, _context12.t2));

          case 10:
            if (!(req.method === 'POST')) {
              _context12.next = 17;
              break;
            }

            _context12.t3 = JsonRespone;
            _context12.t4 = res;
            _context12.next = 15;
            return regeneratorRuntime.awrap(cttr.add(JSON.parse(body)));

          case 15:
            _context12.t5 = _context12.sent;
            return _context12.abrupt("return", (0, _context12.t3)(_context12.t4, _context12.t5));

          case 17:
            _context12.t6 = req.method;
            _context12.next = _context12.t6 === "GET" ? 20 : _context12.t6 === "PUT" ? 26 : _context12.t6 === "DELETE" ? 32 : 38;
            break;

          case 20:
            _context12.t7 = JsonRespone;
            _context12.t8 = res;
            _context12.next = 24;
            return regeneratorRuntime.awrap(cttr.get(unit[2]));

          case 24:
            _context12.t9 = _context12.sent;
            return _context12.abrupt("return", (0, _context12.t7)(_context12.t8, _context12.t9));

          case 26:
            _context12.t10 = JsonRespone;
            _context12.t11 = res;
            _context12.next = 30;
            return regeneratorRuntime.awrap(cttr.update(unit[2], JSON.parse(body)));

          case 30:
            _context12.t12 = _context12.sent;
            return _context12.abrupt("return", (0, _context12.t10)(_context12.t11, _context12.t12));

          case 32:
            _context12.t13 = JsonRespone;
            _context12.t14 = res;
            _context12.next = 36;
            return regeneratorRuntime.awrap(cttr["delete"](unit[2]));

          case 36:
            _context12.t15 = _context12.sent;
            return _context12.abrupt("return", (0, _context12.t13)(_context12.t14, _context12.t15));

          case 38:
          case "end":
            return _context12.stop();
        }
      }
    });
  });
}

module.exports = apiController;