const form = document.querySelector('#search-turno');
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const id = document.querySelector('#id').value;
    fetch(`/turnos/buscar-turno/${id}`)
      .then(response => response.json())
      .then(data => {
        alert(`ID: ${data.id}\nPaciente: ${data.paciente.nombre} ${data.paciente.apellido}\nOdontologo: ${data.odontologo.nombre} ${data.odontologo.apellido}\nFecha: ${data.fecha}\nHora: ${data.hora}`);
                })
                .catch(error => {
                  alert('No se encontró ningún paciente con el ID especificado');
                });
});