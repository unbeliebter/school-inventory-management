export class BasicDialog {
    constructor() {
        this.dialog = document.createElement("dialog");
        this.dialog.classList.add("dialog-notification");
        this.mainContainer = document.createElement("div");
        this.mainContainer.classList.add("dialog-main-container");

        this.dialog.appendChild(this.mainContainer);
        document.body.appendChild(this.dialog);
        this.dialog.setAttribute("open", "false");
    }

    show() {
        this.dialog.setAttribute("open", "true");
    }

    close() {
        this.dialog.setAttribute("open", "false");
    }

    append(element) {
        this.dialog.appendChild(element);
    }

    setBackgroundColor(color) {
        this.dialog.style.backgroundColor = color;
    }
}