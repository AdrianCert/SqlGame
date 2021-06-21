"use strict";

var url_history_Builder = "http://localhost:2021/history/";

function getDate() {
  var d = new Date();
  return "".concat(d.getDate(), "/").concat(d.getMonth() + 1, "/").concat(d.getFullYear(), " - ").concat(d.getHours(), ":").concat(d.getMinutes());
}

function builderHistoryCreate(id_user, question_id) {
  var action, body, response;
  return regeneratorRuntime.async(function builderHistoryCreate$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          console.log(getDate());
          action = "Created question ".concat(question_id, "  ").concat(getDate());
          body = {
            'id': 0,
            'user_id': parseInt(id_user),
            'action': action
          };
          _context.next = 5;
          return regeneratorRuntime.awrap(fetch(url_history_Builder, {
            method: 'POST',
            body: JSON.stringify(body)
          }));

        case 5:
          response = _context.sent;
          console.log(response);

        case 7:
        case "end":
          return _context.stop();
      }
    }
  });
}

function builderHistoryComplete(id_user) {
  var action, body;
  return regeneratorRuntime.async(function builderHistoryComplete$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          action = "Completed question  ".concat(getDate());
          body = {
            'id': 0,
            'user_id': parseInt(id_user),
            'action': action
          };
          _context2.next = 4;
          return regeneratorRuntime.awrap(fetch(url_history_Builder, {
            method: 'POST',
            body: JSON.stringify(body)
          }));

        case 4:
        case "end":
          return _context2.stop();
      }
    }
  });
}

function builderHistoryProfileChange(id_user) {
  var action, body;
  return regeneratorRuntime.async(function builderHistoryProfileChange$(_context3) {
    while (1) {
      switch (_context3.prev = _context3.next) {
        case 0:
          action = "Changed profile  ".concat(getDate());
          body = {
            'id': 0,
            'user_id': parseInt(id_user),
            'action': action
          };
          _context3.next = 4;
          return regeneratorRuntime.awrap(fetch(url_history_Builder, {
            method: 'POST',
            body: JSON.stringify(body)
          }));

        case 4:
        case "end":
          return _context3.stop();
      }
    }
  });
}