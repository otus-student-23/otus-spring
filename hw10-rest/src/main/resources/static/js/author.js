var saveDialog = document.getElementById('save-dialog');
var saveForm = document.forms['save-form'];

document.getElementById('add-button').addEventListener('click', () => {
    saveForm.reset();
    document.getElementById("author.id").value = "";
    document.getElementById("save-result").innerHTML = "";
    saveDialog.showModal();
});

document.getElementById('list-button').addEventListener('click', () => {
    loadData();
});

document.getElementById('save-button-submit').addEventListener('click', () => {
    if (!saveForm.checkValidity()) {
        //saveDialog.reportValidity();
        return;
    };
    var author = {
        id: document.getElementById('author.id').value,
        name: document.getElementById('author.name').value
    };
    fetch('/api/author' + ((author.id === '') ? '' : '/' + author.id), {
        method: (author.id === '') ? 'POST' : 'PUT',
        headers: {
            'accept': '*/*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(author)
    })
    .then(response => {
        if (response.status === 200) {
            loadData();
        } else {
            console.log(response.text());
            document.getElementById("save-result").innerHTML = response.status;
            saveDialog.showModal();
        }
    });
});

document.getElementById('delete-button-submit').addEventListener('click', () => {
    fetch('/api/author/' + document.getElementById('delete.author.id').value, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            loadData();
        } else {
            console.log(response.text());
            document.getElementById("delete-result").innerHTML = response.status;
            document.getElementById('delete-dialog').showModal();
        }
    });
});

function editEntity(id) {
    saveForm.reset();
    document.getElementById("save-result").innerHTML = "";
    fetch('/api/author/' + id)
        .then(response => response.json())
        .then(json => {
            document.getElementById("author.id").value = json.id;
            document.getElementById("author.name").value = json.name;
            saveDialog.showModal();
        })
        .catch((error) => {
            console.log(error);
        });
}

function deleteEntity(id) {
    document.getElementById("delete-result").innerHTML = "";
    document.getElementById('delete.author.id').value = id;
    document.getElementById('delete-dialog').showModal();
}

function loadData() {
    fetch('/api/author')
        .then(response => response.json())
        .then(json => {
            let rows = '';
            json.map(row => {
                rows += `
                    <tr>
                        <td>${row.name}</td>
                        <td>
                            <button onclick='editEntity("${row.id}")'>Править</button>
                            <button onclick='deleteEntity("${row.id}")'>Удалить</button>
                        </td>
                    </tr>`;
            })
            document.getElementById('authors').innerHTML = rows;
        })
        .catch((error) => {
            console.log(error);
        });
}

loadData();