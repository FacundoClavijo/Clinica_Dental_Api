const form = document.querySelector('#search-paciente');
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const id = document.querySelector('#id').value;
    fetch(`/pacientes/buscar-paciente/${id}`)
      .then(response => response.json())
      .then(data => {
        alert(`ID: ${data.id}\nNombre: ${data.nombre}\nApellido: ${data.apellido}\nDNI: ${data.dni}\nFecha de Alta: ${data.fechaAlta}\nDomicilio: ${data.domicilio.calle} ${data.domicilio.numero}, ${data.domicilio.localidad}`);
                })
                .catch(error => {
                  alert('No se encontró ningún paciente con el ID especificado');
                });
            });

