"use strict";

var url = "http://localhost:2021/question/";
/**
 * De facut - butonul de descarcare a fisierului 
 * functia valideaza nu face nimic
 */

var send_body = {
  'id': 0,
  'title': "",
  'description': "",
  'solution': "",
  'value': 20,
  'reward': 0
};

function build_body(entity) {
  send_body["title"] = entity["title"];
  send_body["description"] = entity["enunt"];
  send_body["solution"] = entity["solutia"];
  send_body["value"] = parseInt(entity["costa"]);
  send_body["reward"] = entity["costa"] * 2;
}

var data = function data(ev) {
  var current_user_id, entity;
  return regeneratorRuntime.async(function data$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          ev.preventDefault();
          _context.next = 3;
          return regeneratorRuntime.awrap(didIGetIt());

        case 3:
          current_user_id = _context.sent;
          entity = {
            'title': document.getElementById("titlu").value,
            'enunt': document.getElementById("enunt").value,
            'solutia': document.getElementById("solutia").value,
            'costa': document.getElementById("costa").value
          };
          build_body(entity);
          _context.next = 8;
          return regeneratorRuntime.awrap(fetch(url, {
            method: 'POST',
            body: JSON.stringify(send_body)
          }).then(function (r) {
            return r.json();
          }).then(function (r) {
            console.log(r);
            document.getElementById("titlu").value = '';
            document.getElementById("solutia").value = '';
            document.getElementById("enunt").value = '';

            if (r.status != 404) {
              builderHistoryCreate(parseInt(current_user_id.id), parseInt(r.id));
              window.location = '/question/';
            }
          }));

        case 8:
        case "end":
          return _context.stop();
      }
    }
  });
};

function getFragment(str) {
  return document.createRange().createContextualFragment(str);
}

function valideaza() {
  fetch("/api/querry/getcsv/", {
    method: "POST",
    body: JSON.stringify({
      "querry": document.getElementById("solutia").value,
      "lid": 21
    })
  }).then(fetchDownload);
}

document.addEventListener("DOMContentLoaded", function _callee() {
  var current_user_id;
  return regeneratorRuntime.async(function _callee$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          _context2.next = 2;
          return regeneratorRuntime.awrap(didIGetIt());

        case 2:
          current_user_id = _context2.sent;
          writeProfileCard(current_user_id.id);
          writeClassamentCard();
          document.getElementById("submit").addEventListener('click', data);
          document.getElementById("check").addEventListener('click', valideaza);

        case 7:
        case "end":
          return _context2.stop();
      }
    }
  });
});