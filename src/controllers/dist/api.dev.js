"use strict";

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance"); }

function _iterableToArray(iter) { if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === "[object Arguments]") return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } }

var Router = require('./../routing');

var api = require('./../binder/api');

var queryApi = require('./../binder/queryHandler');

var locations = Object.keys(api);

function homeView(req, res) {
  JsonRespone(res);
}

function whoIAm(req, res) {
  var data;
  return regeneratorRuntime.async(function whoIAm$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          if (!(req.headers.hasOwnProperty('auth') && req.headers.auth.is_authenticated)) {
            _context.next = 6;
            break;
          }

          _context.next = 3;
          return regeneratorRuntime.awrap(api.user.get(req.headers.auth.info.user));

        case 3:
          _context.t0 = _context.sent;
          _context.next = 7;
          break;

        case 6:
          _context.t0 = {};

        case 7:
          data = _context.t0;
          return _context.abrupt("return", JsonRespone(res, data));

        case 9:
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

function apiController(req, res) {
  var nrout;
  return regeneratorRuntime.async(function apiController$(_context7) {
    while (1) {
      switch (_context7.prev = _context7.next) {
        case 0:
          nrout = /\/api\/(\w+)\/(.*)/gm.exec(req.url);

          if (!(nrout !== null && locations.includes(nrout[1]))) {
            _context7.next = 3;
            break;
          }

          return _context7.abrupt("return", serveApi(req, res, nrout));

        case 3:
          return _context7.abrupt("return", new Router().path(/\/api\/$/gm, homeView).path(/\/api\/whoIAm$/gm, whoIAm).path("/api/querry/check/", checkQuestionAnswer).path("/api/querry/getcsv/", downloadCSVResult).route(req, res));

        case 4:
        case "end":
          return _context7.stop();
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
  }).on('end', function _callee3() {
    var cttr;
    return regeneratorRuntime.async(function _callee3$(_context8) {
      while (1) {
        switch (_context8.prev = _context8.next) {
          case 0:
            body = Buffer.concat(body).toString();
            body = JSON.parse(body);
            cttr = api[unit[1]];

            if (!(unit[2].length === 0)) {
              _context8.next = 18;
              break;
            }

            if (!(req.method === 'GET')) {
              _context8.next = 11;
              break;
            }

            _context8.t0 = JsonRespone;
            _context8.t1 = res;
            _context8.next = 9;
            return regeneratorRuntime.awrap(cttr.getAll());

          case 9:
            _context8.t2 = _context8.sent;
            return _context8.abrupt("return", (0, _context8.t0)(_context8.t1, _context8.t2));

          case 11:
            if (!(req.method === 'POST')) {
              _context8.next = 18;
              break;
            }

            _context8.t3 = JsonRespone;
            _context8.t4 = res;
            _context8.next = 16;
            return regeneratorRuntime.awrap(cttr.add(body));

          case 16:
            _context8.t5 = _context8.sent;
            return _context8.abrupt("return", (0, _context8.t3)(_context8.t4, _context8.t5));

          case 18:
            _context8.t6 = req.method;
            _context8.next = _context8.t6 === "GET" ? 21 : _context8.t6 === "PUT" ? 27 : _context8.t6 === "DELETE" ? 33 : 39;
            break;

          case 21:
            _context8.t7 = JsonRespone;
            _context8.t8 = res;
            _context8.next = 25;
            return regeneratorRuntime.awrap(cttr.get(unit[2]));

          case 25:
            _context8.t9 = _context8.sent;
            return _context8.abrupt("return", (0, _context8.t7)(_context8.t8, _context8.t9));

          case 27:
            _context8.t10 = JsonRespone;
            _context8.t11 = res;
            _context8.next = 31;
            return regeneratorRuntime.awrap(cttr.update(unit[2], body));

          case 31:
            _context8.t12 = _context8.sent;
            return _context8.abrupt("return", (0, _context8.t10)(_context8.t11, _context8.t12));

          case 33:
            _context8.t13 = JsonRespone;
            _context8.t14 = res;
            _context8.next = 37;
            return regeneratorRuntime.awrap(cttr["delete"](unit[2]));

          case 37:
            _context8.t15 = _context8.sent;
            return _context8.abrupt("return", (0, _context8.t13)(_context8.t14, _context8.t15));

          case 39:
          case "end":
            return _context8.stop();
        }
      }
    });
  });
}

module.exports = apiController;