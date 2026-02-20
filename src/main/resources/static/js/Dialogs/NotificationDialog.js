class NotificationDialog {
    constructor() {
        this.dialog = document.createElement("dialog");
        this.dialog.classList.add("dialog-notification");
        this.mainContainer = document.createElement("div");
        this.mainContainer.classList.add("dialog-main-container");
        this.messageLabel = document.createElement("label");
        this.buttonRow = new ButtonRow();

        this.dialog.appendChild(this.messageLabel);
        this.dialog.appendChild(this.mainContainer);
        this.mainContainer.appendChild(this.buttonRow.div);
        document.body.appendChild(this.dialog);
        this.dialog.setAttribute("open", "false");
    }

    showError = (message) => {
        return new Promise((resolve) => {
            this.dialog.setAttribute("error", "");
            this.messageLabel.textContent = message;
            this.mainContainer.replaceChildren();

            let okButton = new ConfirmButton();
            okButton.addEventListener("click", () => {
                resolve(true);
                this.dialog.removeAttribute("error");
                this.close();
            })
            this.buttonRow.replaceChildren(okButton);

            this.dialog.setAttribute("open","true");
        });
    }
    
    showConfirm = (message) => {
        return new Promise((resolve) => {
            this.messageLabel.innerHTML = message;
            this.mainContainer.replaceChildren();
            this.buttonRow = new ConfirmRow();
            this.mainContainer.appendChild(this.buttonRow.div);
            this.dialog.setAttribute("open","true");

            this.buttonRow.okButton.addEventListener("click", () => {
                resolve(true);
                this.close();
            })
            this.buttonRow.cancelButton.addEventListener("click", () => {
                resolve(false);
                this.close();
            })
        })
    }

    showNotification = (message) => {
        return new Promise((resolve) => {
            this.messageLabel.innerHTML = message;
            this.mainContainer.replaceChildren();
            this.buttonRow = new OkRow();
            this.mainContainer.appendChild(this.buttonRow.div);
            this.dialog.setAttribute("open","true");

            this.buttonRow.okButton.addEventListener("click", () => {
                resolve(true);
                this.close();
            })
        })
    }

    showInitialPassword = (message, initPw) => {
        return new Promise((resolve) => {
            this.messageLabel.innerHTML = message;
            this.mainContainer.replaceChildren();
            this.buttonRow = new OkRow();
            this.buttonRow.div.appendChild(new CopyButton(initPw));
            this.mainContainer.appendChild(this.buttonRow.div);
            this.dialog.setAttribute("open","true");

            this.buttonRow.okButton.addEventListener("click", () => {
                resolve(true);
                this.close();
            })
        })
    }

    showSingleInput = (message, prefilledText) => {
        return new Promise((resolve) => {
            this.messageLabel.textContent = message;

            let input = new TextInput(prefilledText);
            this.buttonRow = new ButtonRow();
            this.mainContainer.replaceChildren(input, this.buttonRow.div);

            let okButton = new ConfirmButton();
            okButton.addEventListener("click", () => {
                resolve(input.value);
                this.close();
            })
            let cancelButton = new CancelButton();
            cancelButton.addEventListener("click", () => {
                resolve(false);
                this.close();
            })
            this.buttonRow.replaceChildren([okButton, cancelButton]);

            this.dialog.setAttribute("open","true");
        });
    }

    close() {
        this.dialog.setAttribute("open", "false");
    }
}

class ButtonRow {
    constructor() {
        this.div = document.createElement("div");
        this.div.classList.add("dialog-button-row");
    }

    addButton(button) {
        this.div.appendChild(button);
    }

    replaceChildren(newChildrenList) {
        this.div.replaceChildren();
        for (let child of newChildrenList) {
            this.addButton(child);
        }
    }
}

class OkRow {
    constructor() {
        this.div = document.createElement("div");
        this.div.classList.add("dialog-button-row");
        this.okButton = new ConfirmButton();

        this.div.appendChild(this.okButton);
    }
}

class ConfirmRow {
    constructor() {
        this.div = document.createElement("div");
        this.div.classList.add("dialog-button-row");
        this.okButton = new ConfirmButton();
        this.cancelButton = new CancelButton();

        this.div.appendChild(this.okButton);
        this.div.appendChild(this.cancelButton);

    }
}

class ConfirmButton {
    constructor() {
        this.button = document.createElement("button");
        this.button.innerHTML = "OK";
        this.button.classList.add("submit");
        this.button.setAttribute("type", "submit");
        return this.button;
    }
}

class CancelButton {
    constructor() {
        this.button = document.createElement("button");
        this.button.innerHTML = "Abbrechen";
        this.button.classList.add("cancel");
        this.button.setAttribute("type", "reset");
        return this.button;
    }
}


class CopyButton {
    constructor(textToCopy) {
        this.div = document.createElement("div");

        this.textToCopy = textToCopy;
        this.labelCopyConfirmation = document.createElement("label");
        this.labelCopyConfirmation.textContent="Kopiert!"
        this.labelCopyConfirmation.classList.add("confirmation-text-floating");
        this.#turnConfirmationOff();

        this.img = document.createElement("img");
        this.img.src = "../../icons/copy.svg";
        this.img.classList.add("button-link");
        this.img.addEventListener("click", () => {
            navigator.clipboard.writeText(this.textToCopy)
            this.#showCopyConfirmation();
        });

        this.div.appendChild(this.labelCopyConfirmation);
        this.div.appendChild(this.img);
        return this.div;
    }

    #showCopyConfirmation = () => {
        this.labelCopyConfirmation.style.visibility = "visible";
        this.labelCopyConfirmation.setAttribute("show", "true");
        let confirmationDisplayTimeMs = 1000;
        setTimeout(this.#turnConfirmationOff, confirmationDisplayTimeMs);
    }

    #turnConfirmationOff = () => {
        this.labelCopyConfirmation.setAttribute("show", "false");
        this.labelCopyConfirmation.style.visibility = "hidden";
    }
}

class TextInput {
    constructor(prefilledText) {
        let input = document.createElement("input");
        input.type = "text";
        input.value = prefilledText;
        input.style.textAlign = "center";
        return input;
    }
}


const notificationDialog = new NotificationDialog();