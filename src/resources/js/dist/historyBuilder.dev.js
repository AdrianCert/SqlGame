"use strict";

var url_history_Builder = "http://localhost:2021/history/";

function remove(date) {
  var cdate = date.toString();
  return cdate.replace("GMT+0300 (Eastern European Summer Time)", "");
}

function builderHistoryCreate(id_user, question_id) {
  var date, action, body;
  return regeneratorRuntime.async(function builderHistoryCreate$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          date = Date();
          action = "Created question ".concat(question_id, " - ").concat(remove(date));
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
        case "end":
          return _context.stop();
      }
    }
  });
}

function builderHistoryComplete(id_user) {
  var date, action, body;
  return regeneratorRuntime.async(function builderHistoryComplete$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          date = Date();
          action = "Completed question - ".concat(remove(date));
          body = {
            'id': 0,
            'user_id': parseInt(id_user),
            'action': action
          };
          _context2.next = 5;
          return regeneratorRuntime.awrap(fetch(url_history_Builder, {
            method: 'POST',
            body: JSON.stringify(body)
          }));

        case 5:
        case "end":
          return _context2.stop();
      }
    }
  });
}

function builderHistoryProfileChange(id_user) {
  var date, action, body;
  return regeneratorRuntime.async(function builderHistoryProfileChange$(_context3) {
    while (1) {
      switch (_context3.prev = _context3.next) {
        case 0:
          date = Date();
          action = "Changed profile - ".concat(remove(date));
          body = {
            'id': 0,
            'user_id': parseInt(id_user),
            'action': action
          };
          _context3.next = 5;
          return regeneratorRuntime.awrap(fetch(url_history_Builder, {
            method: 'POST',
            body: JSON.stringify(body)
          }));

        case 5:
        case "end":
          return _context3.stop();
      }
    }
  });
}