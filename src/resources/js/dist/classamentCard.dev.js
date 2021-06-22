"use strict";

function getFragment(str) {
  return document.createRange().createContextualFragment(str);
}

function replaceClassamentCard(classament) {
  var counter = 1;
  var container = document.getElementById("classamentCard");

  while (container.firstChild) {
    container.firstChild.remove();
  }

  container.appendChild(document.createRange().createContextualFragment("<h2>Clasament</h2>"));
  classament.forEach(function (u) {
    if (counter < 6) {
      container.appendChild(getFragment("<div class=\"row\">\n                <p id = \"row1\">".concat(counter++, ". ").concat(u.nickname, " (").concat(u.coins, ")</p>\n            </div>")));
    }
  });
  container.appendChild(getFragment("\n            <br>\n            <hr>\n            <div class=\"row_button\">\n                <button id = \"downloadClasament\">Descarca</button>\n            </div>\n        "));
  container.querySelector("#downloadClasament").addEventListener('click', descarcaClasament);
}

function writeClassamentCard() {
  var classament;
  return regeneratorRuntime.async(function writeClassamentCard$(_context) {
    while (1) {
      switch (_context.prev = _context.next) {
        case 0:
          _context.next = 2;
          return regeneratorRuntime.awrap(getClassament());

        case 2:
          classament = _context.sent;
          replaceClassamentCard(classament);

        case 4:
        case "end":
          return _context.stop();
      }
    }
  });
}