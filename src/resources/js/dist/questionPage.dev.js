"use strict";

var url_question_by_id = "http://localhost:2021/question/{id}";
var url_API = "/api/querry/check/".concat(getCurrentQuestionId());

function descarcaFisier(e) {
  fetch("/api/querry/getcsv/", {
    method: "POST",
    body: JSON.stringify({
      "querry": document.getElementById("solutia").value,
      "qid": 0
    })
  }).then(fetchDownload);
}

var querrySender = function querrySender(ev) {
  var tof;
  return regeneratorRuntime.async(function querrySender$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          ev.preventDefault();
          _context.next = 3;
          return regeneratorRuntime.awrap(fetch(url_API, {
            method: "POST",
            body: document.getElementById("solutia").value
          }).then(function (r) {
            return r.json();
          }));

        case 3:
          tof = _context.sent;
          sendMessageToUser(tof);

        case 5:
        case "end":
          return _context.stop();
      }
    }
  });
};

function valid() {
  return getFragment("\n        <p style=\"color:green\">VALID</p>\n        ");
}

function eroare() {
  return getFragment("\n        <p style=\"color:red\">EROARE</p>\n        ");
}

function sendMessageToUser(tof) {
  var container, current_user_id;
  return regeneratorRuntime.async(function sendMessageToUser$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          container = document.getElementById("validare");

          while (container.firstChild) {
            container.firstChild.remove();
          }

          if (!tof.accepted) {
            _context2.next = 10;
            break;
          }

          container.appendChild(valid());
          _context2.next = 6;
          return regeneratorRuntime.awrap(didIGetIt());

        case 6:
          current_user_id = _context2.sent;
          builderHistoryComplete(parseInt(current_user_id.id));
          _context2.next = 11;
          break;

        case 10:
          container.appendChild(eroare());

        case 11:
          document.getElementById("validare").style.display = 'block';

        case 12:
        case "end":
          return _context2.stop();
      }
    }
  });
}

function getCurrentQuestionId() {
  return parseInt(window.location.pathname.replace("/question/", ""));
}

function getFragment(str) {
  return document.createRange().createContextualFragment(str);
}

function getQuestionDetails() {
  var current_id, url_;
  return regeneratorRuntime.async(function getQuestionDetails$(_context3) {
    while (1) {
      switch (_context3.prev = _context3.next) {
        case 0:
          current_id = getCurrentQuestionId();
          url_ = url_question_by_id.replace("{id}", current_id);
          return _context3.abrupt("return", fetch(url_, {
            method: 'GET'
          }).then(function (r) {
            return r.json();
          }));

        case 3:
        case "end":
          return _context3.stop();
      }
    }
  });
}

function pageQuestion_replace() {
  var details, container1, container2;
  return regeneratorRuntime.async(function pageQuestion_replace$(_context4) {
    while (1) {
      switch (_context4.prev = _context4.next) {
        case 0:
          _context4.next = 2;
          return regeneratorRuntime.awrap(getQuestionDetails());

        case 2:
          details = _context4.sent;
          container1 = document.getElementById("first_row");

          while (container1.firstChild) {
            container1.firstChild.remove();
          }

          container1.appendChild(getFragment("\n        <p>".concat(details.title, "</p>\n        ")));
          container2 = document.getElementById("second_row");

          while (container2.firstChild) {
            container2.firstChild.remove();
          }

          container2.appendChild(getFragment("\n        <p>".concat(details.description, "</p>\n        ")));

        case 9:
        case "end":
          return _context4.stop();
      }
    }
  });
}

function descarcaClasament() {
  console.log("AICI");
}

document.addEventListener("DOMContentLoaded", function _callee() {
  var current_user_id;
  return regeneratorRuntime.async(function _callee$(_context5) {
    while (1) {
      switch (_context5.prev = _context5.next) {
        case 0:
          _context5.next = 2;
          return regeneratorRuntime.awrap(didIGetIt());

        case 2:
          current_user_id = _context5.sent;
          writeProfileCard(current_user_id.id);
          writeClassamentCard();
          pageQuestion_replace();
          document.getElementById("raspuns").addEventListener("click", querrySender);
          document.getElementById("descarcaFisier").addEventListener("click", descarcaFisier);

        case 8:
        case "end":
          return _context5.stop();
      }
    }
  });
});