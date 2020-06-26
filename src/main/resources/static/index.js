const BASE_URL = "http://localhost:8081";

(function () {

    function displayOutput(element, { data }) {
        document.getElementById(element).innerText = JSON.stringify(data, undefined, 2);
    }

    document.getElementById('createForm').addEventListener('submit', function (event) {
        event.preventDefault();
        let data = {};
        for (element of this) {
            data[element.name] = element.value
        }
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

    document.getElementById('updateForm').addEventListener('submit', function (event) {
        event.preventDefault();
        let data = {};
        for (element of this) {
            data[element.name] = element.value
        }
        axios.put(`${BASE_URL}/duck/updateDuck?id=${data.id}`, data)
            .then(res => displayOutput("updateOutput", res)
            ).catch(err => console.error(err));
    });

    document.getElementById('deleteButton').addEventListener('click', function () {
        axios.delete(`${BASE_URL}/duck/deleteDuck/${document.getElementById('deleteInput').value}`)
            .then(res =>
                displayOutput("deleteOutput", res)
            ).catch(err => console.error(err));
    });
})()