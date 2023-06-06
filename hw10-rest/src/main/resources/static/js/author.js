var saveDialog = document.getElementById('save-dialog');
var saveForm = document.forms['save-form'];

document.getElementById('button-add').addEventListener('click', () => {
    saveForm.reset();
    //document.getElementById("resultMsg").innerHTML = "";//TODO remove
    saveDialog.showModal();
});

document.getElementById('button-refresh').addEventListener('click', () => {
    reloadData();
});

document.getElementById('button-add-submit').addEventListener('click', () => {
    if (!saveForm.checkValidity()) {
        //saveDialog.reportValidity();
        return;
    };
    var author = { name: document.getElementById('author.name').value };
    fetch('/api/author', {
        method: 'POST',
        headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(author)
    })
    .then(response => {
        if (response.ok) {
            //saveDialog.close();
            reloadData();
        } else {
            document.getElementById("resultMsg").innerHTML = response.text();//TODO странная ошибка, не из тела ответа
            saveDialog.showModal();//TODO почему-то форма после submit закрывается
        }
    });
});

document.getElementById('button-add-cancel').addEventListener('click', () => {
    saveDialog.close();
});

function editEntity(id) {
    alert(id);
}
function deleteEntity(id) {
    alert(id);
}

function reloadData() {
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
            console.log(rows);//TODO remove
        }).catch((error) => {
            console.log(error);
        });
}

reloadData();