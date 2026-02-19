import {BasicDialog} from "./Dialogs/BasicDialog.js";
// import {ConfirmButton} from "./Dialogs/NotificationDialog.js";

const ICON_PATH = "../../icons/";

class ColumnFilterToggle {
    #showIcon = ICON_PATH + "eye.svg";
    #dontShowIcon = ICON_PATH + "eye-off.svg";
    #isOn;
    #manager;
    #columnName;


    constructor(columnName, columnManager) {
        this.#manager = columnManager;
        this.#columnName = columnName;

        let div = document.createElement("div");
        let label = document.createElement("label");
        this.imgButton = document.createElement("img");

        div.style.display = "flex";
        div.style.justifyContent = "space-between";

        label.textContent = columnName !== "" ? columnName : "Steuerung";
        this.imgButton.src = this.#showIcon;
        this.#isOn = true;
        this.imgButton.classList.add("button-link");
        this.imgButton.title = "Spalten filtern"
        this.imgButton.alt = "column filter";

        this.imgButton.addEventListener("click", () => {
            this.#isOn = !this.#isOn;
            this.imgButton.src = this.#isOn ? this.#showIcon : this.#dontShowIcon;
            this.#manager.toggleColumn(this.#columnName);
        })

        div.appendChild(label);
        div.appendChild(this.imgButton);

        return div;
    }
}

class TableColumnManager {

    #dialog = new BasicDialog();
    #tableHeaderMap = new Map();
    #tableHeaderNameToIndex = new Map();
    #tableRows = document.getElementsByTagName("tr");

    constructor() {
        let openColumnFilterButton = document.getElementById("openColumnFilter");
        if (openColumnFilterButton === null) {
            return;
        }
        openColumnFilterButton.addEventListener("click", () => {
            this.#dialog.show();
        });

        let table = document.getElementsByTagName("table")[0];
        let tableHeader = table.getElementsByTagName("thead")[0];
        let tableHeaderRow = tableHeader.firstElementChild;

        for (let i = 0; i < tableHeaderRow.children.length; i++) {
            let child = tableHeaderRow.children[i];
            if (child.tagName.toUpperCase() === "TH") {
                this.#tableHeaderMap.set(child.innerHTML, child);
                this.#tableHeaderNameToIndex.set(child.innerHTML, i);

                this.#dialog.append(new ColumnFilterToggle(child.innerHTML, this));
            }
        }
        let doneButton = new ConfirmButton();
        doneButton.textContent = "Fertig";
        doneButton.addEventListener("click", () => {
            this.#dialog.close();
        })
        this.#dialog.append(doneButton);
    }

    toggleColumn = (columnName) => {
        let tableHead = this.#tableHeaderMap.get(columnName);
        if (tableHead == null) {
            return;
        }
        let newDisplay = tableHead.style.display === "none" ? "table-cell" : "none";
        tableHead.style.display = newDisplay;
        let colNumber = this.#tableHeaderNameToIndex.get(columnName);
        for (let row of this.#tableRows) {
            row.children[colNumber].style.display = newDisplay;
        }
    }
}


const tableColumnManager = new TableColumnManager();