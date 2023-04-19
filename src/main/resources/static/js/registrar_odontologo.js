let form = document.querySelector('#add_new_odontologo');
console.log("Estoy aquí")
form.addEventListener('submit' , function(event){
    event.preventDefault();
    const data = {
       nombre : document.querySelector('#nombre').value,
       apellido : document.querySelector('#apellido').value,
       nroMatricula : document.querySelector('#nroMatricula').value
    };

    const setting = {
    method : 'POST',
    headers: {
        'Content-Type': 'application/json'},
    body : JSON.stringify(data)
    }

    fetch( '/odontologos/registrar-odontologo', setting )
    .then(response => response.json())
    .then( dataResponse => {
                             console.log(response.status)

                             console.log(dataResponse.status)})
    .catch(error => console.log(error))

    form.reset;
})