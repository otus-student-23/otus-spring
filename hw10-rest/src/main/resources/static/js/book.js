const api = '/api/book';

function getEntityJson() {
    return {
        id: document.getElementById('entity.id').value,
        name: document.getElementById('entity.name').value,
        author: {
            name: document.getElementById('author').value
        },
        genre: {
            name: document.getElementById('genre').value
        }
    };
}

function fillEntityForm(json) {
    document.getElementById('entity.id').value = json.id;
    document.getElementById('entity.name').value = json.name;
    document.getElementById('author').value = json.author.name;
    document.getElementById('genre').value = json.genre.name;
}

function getEntitiesRows(json) {
    fillInputOptions('author');
    fillInputOptions('genre');

    let rows = '';
    json.map(row => {
        rows += `
            <tr>
                <td><a href='#' onclick='showEntity("${row.id}")'>${row.name}</a></td>
                <td>${row.author.name}</td>
                <td>${row.genre.name}</td>
                <td>
                    <a href='/comment.html?bookId=${row.id}'><button>Комментарии</button></a>
                    <button onclick='editEntity("${row.id}")'>Править</button>
                    <button onclick='deleteEntity("${row.id}")'>Удалить</button>
                </td>
            </tr>`;
    })
    return rows;
}

function fillInputOptions(option_name) {
    fetch('/api/' + option_name)
        .then(response => response.json())
        .then(json => {
            let rows = '';
            json.map(row => {
                rows += `<option value="${row.name}">`;
            });
            document.getElementById(option_name + 's').innerHTML = rows;
        })
        .catch((error) => {
            console.log(error);
        });
}