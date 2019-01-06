function parseQuery() {
    let queryValue = document.getElementById("query").value;
    let regex = /[a-z]{3}/;
    let isProperQuery = regex.test(queryValue);
    let button = document.getElementById("submitButton");
    if (isProperQuery) {
        button.disabled = false;
    } else {
        button.disabled = true;
    }
}