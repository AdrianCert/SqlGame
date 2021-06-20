"use strict";

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance"); }

function _iterableToArray(iter) { if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === "[object Arguments]") return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } }

document.addEventListener("DOMContentLoaded", function () {
  writeQuestions(3);
  document.getElementById("dificultate").addEventListener("change", selector);
  document.getElementById("text").addEventListener("change", inputText);
});
var __questions__ = null;

function getQuestions() {
  return regeneratorRuntime.async(function getQuestions$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          if (!(__questions__ === null)) {
            _context.next = 6;
            break;
          }

          _context.next = 3;
          return regeneratorRuntime.awrap(binder_api.question.getAll().then(function (r) {
            return r.json();
          }));

        case 3:
          _context.t0 = _context.sent;
          _context.next = 7;
          break;

        case 6:
          _context.t0 = __questions__;

        case 7:
          __questions__ = _context.t0;
          return _context.abrupt("return", _toConsumableArray(__questions__));

        case 9:
        case "end":
          return _context.stop();
      }
    }
  });
}

function getQuestion(id) {
  id = parseInt(id);
  return __questions__.filter(function (q) {
    return q.id === id;
  })[0];
}

function createQuestionBox(data) {
  var doc = document.createElement("div");
  doc.classList.add("question-box");
  doc.dataset.entity_id = data.id;
  var title = document.createElement('h4');
  title.textContent = data.title;
  doc.appendChild(title);
  var description = document.createElement('p');
  description.innerHTML = data.description;
  doc.appendChild(description);
  var nfobox = document.createElement('div');
  nfobox.classList.add('nfo');
  doc.appendChild(nfobox);
  nfobox.appendChild(document.createRange().createContextualFragment("<p>#".concat(data.id, "</p><p>").concat(data.value, "/").concat(data.reward, "</p>")));
  doc.addEventListener("click", showQuestionModal);
  return doc;
}

function showQuestionModal(e) {
  var question_box = e.target;
  var qid = 0;

  while (qid < e.path.length - 2) {
    question_box = e.path[qid++];
    if (question_box.classList.contains('question-box')) break;
    question_box = null;
  }

  if (question_box === null) return;
  qid = question_box.dataset.entity_id;
  var question_data = getQuestion(qid);
  var modal = getContextModal("question_modal_id");
  modal.style.display = "block";
  modal.querySelector(".modal-header h2").textContent = question_data.title;
  modal.querySelector(".modal-body").textContent = question_data.description; // <button type="button" class="">default</button>

  var btn_classes = "btn btn-round btn-default";
  var buttons = document.createElement('div');
  buttons.classList.add('space-around');
  var view_btn = document.createElement('button');
  view_btn.type = 'button';
  view_btn.className = btn_classes;
  view_btn.textContent = "view";
  view_btn.addEventListener('click', function () {
    console.log(qid);
    window.location = "/question/".concat(qid);
  });
  buttons.appendChild(view_btn);
  var buy_btn = document.createElement('button');
  buy_btn.type = 'button';
  buy_btn.className = btn_classes;
  buy_btn.textContent = "".concat(question_data.value, " coins");
  buy_btn.addEventListener('click', function () {
    window.location = '/';
  });
  buttons.appendChild(buy_btn);

  if (modal.querySelector(".modal-footer").firstChild !== null) {
    modal.querySelector(".modal-footer").firstChild.remove();
  }

  modal.querySelector(".modal-footer").appendChild(buttons);
}

var selector = function selector(ev) {
  var doc, listQuestions, sortedList;
  return regeneratorRuntime.async(function selector$(_context2) {
    while (1) {
      switch (_context2.prev = _context2.next) {
        case 0:
          doc = document.getElementById("dificultate");
          _context2.next = 3;
          return regeneratorRuntime.awrap(getQuestions());

        case 3:
          listQuestions = _context2.sent;
          sortedList = listQuestions.filter(function (r) {
            return r.value == doc.value;
          });
          if (doc.value == 0) writeQuestions(3, listQuestions);else writeFitredQuestion(sortedList);

        case 6:
        case "end":
          return _context2.stop();
      }
    }
  });
};

var inputText = function inputText(ev) {
  var doc, listQuestions, sortedList;
  return regeneratorRuntime.async(function inputText$(_context3) {
    while (1) {
      switch (_context3.prev = _context3.next) {
        case 0:
          doc = document.getElementById("text");
          _context3.next = 3;
          return regeneratorRuntime.awrap(getQuestions());

        case 3:
          listQuestions = _context3.sent;
          sortedList = listQuestions.filter(function (r) {
            return r.title.includes(doc.value);
          });
          if (sortedList.length != 0) writeFitredQuestion(sortedList);

        case 6:
        case "end":
          return _context3.stop();
      }
    }
  });
};

function writeFitredQuestion(list) {
  var doc;
  return regeneratorRuntime.async(function writeFitredQuestion$(_context4) {
    while (1) {
      switch (_context4.prev = _context4.next) {
        case 0:
          doc = document.getElementById("main");

          while (doc.firstChild) {
            doc.firstChild.remove();
          }

          writeQuestions(3, list);

        case 3:
        case "end":
          return _context4.stop();
      }
    }
  });
}

function writeQuestions(n) {
  var data,
      container,
      curr,
      slots,
      _args5 = arguments;
  return regeneratorRuntime.async(function writeQuestions$(_context5) {
    while (1) {
      switch (_context5.prev = _context5.next) {
        case 0:
          data = _args5.length > 1 && _args5[1] !== undefined ? _args5[1] : null;

          if (!(data === null)) {
            _context5.next = 7;
            break;
          }

          _context5.next = 4;
          return regeneratorRuntime.awrap(getQuestions());

        case 4:
          _context5.t0 = _context5.sent;
          _context5.next = 8;
          break;

        case 7:
          _context5.t0 = data;

        case 8:
          data = _context5.t0;
          console.log(data);
          container = document.getElementById('main');
          curr = 0; // create slots and add recusing each outher

          slots = _toConsumableArray(Array(n).keys()).map(function (s) {
            var c = document.createElement('div');
            c.setAttribute('id', "qestion_box_slot_id_".concat(s));
            c.classList.add('question-slot');
            c.style.width = "calc(".concat(100 / n, "% - 16px)");
            container.appendChild(c);
            return c;
          });

          _toConsumableArray(data).forEach(function (q) {
            if (curr === n) curr = 0;
            slots[curr++].appendChild(createQuestionBox(q));
          });

        case 14:
        case "end":
          return _context5.stop();
      }
    }
  });
}