function eliminarPaciente(id) {
    if (confirm("¿Está seguro de que desea eliminar este paciente?")) {
        fetch("/pacientes/eliminar-paciente/" + id, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert("Ocurrió un error al eliminar el paciente.");
            }
        });
    }
}