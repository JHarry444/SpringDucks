const BASE_URL = "";

(function () {

    function displayOutput(element, {data}) {
        document.getElementById(element).innerText = JSON.stringify(data, undefined, 2);
    }

    document.getElementById('createButton').addEventListener('click', function () {
        let data = {};
        document.querySelectorAll('#create > input').forEach(el => data[el.name] = el.value);
        axios.post(BASE_URL + '/duck/createDuck', data)
            .then(res => displayOutput("createOutput", res)
            ).catch(err => console.error(err));
    });

    document.getElementById('readButton').addEventListener('click', function () {
        axios.get(BASE_URL + '/duck/getAll')
            .then(res =>
                document.getElementById('readOutput').innerText = JSON.stringify(res.data)
            ).catch(err => console.error(err));
    });

    document.getElementById('deleteButton').addEventListener('click', function () {
        axios.delete(`${BASE_URL}/duck/deleteDuck/${document.getElementById('deleteInput').value}`)
            .then(res =>
                displayOutput("deleteOutput", res)
            ).catch(err => console.error(err));
    });
})()