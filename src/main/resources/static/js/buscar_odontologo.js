const form = document.querySelector('#search-odontologo');
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const id = document.querySelector('#id').value;

    fetch(`/odontologos/buscar-odontologo/${id}`)
      .then(response => response.json())
      .then(data => {
        alert(`ID: ${data.id}\nNombre: ${data.nombre}\nApellido: ${data.apellido}\nMatrícula: ${data.nroMatricula}`);
      })
      .catch(error => {
        alert('No se encontró ningún odontólogo con el ID especificado');
      });
  });