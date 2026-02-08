class NotificationDialog {
    constructor() {
        this.dialog = document.createElement("dialog");
        this.dialog.classList.add("dialog-notification");
        this.mainContainer = document.createElement("div");
        this.mainContainer.classList.add("dialog-main-container");
        this.messageLabel = document.createElement("label");
        this.buttonRow = new ConfirmRow();

        this.dialog.appendChild(this.messageLabel);
        this.dialog.appendChild(this.mainContainer);
        this.mainContainer.appendChild(this.buttonRow.div);
        document.body.appendChild(this.dialog);
        this.dialog.setAttribute("open", "false");
    }
    
    showConfirm = (message) => {
        return new Promise((resolve) => {
            this.messageLabel.innerHTML = message;
            this.mainContainer.removeChild(this.buttonRow.div);
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
            this.mainContainer.removeChild(this.buttonRow.div);
            this.buttonRow = new OkRow();
            this.mainContainer.appendChild(this.buttonRow.div);
            this.dialog.setAttribute("open","true");

            this.buttonRow.okButton.addEventListener("click", () => {
                resolve(true);
                this.close();
            })
        })
        
    }

    close() {
        this.dialog.setAttribute("open", "false");
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


const notificationDialog = new NotificationDialog();