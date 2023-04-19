function eliminarTurno(id) {
    if (confirm("¿Está seguro de que desea eliminar este turno?")) {
        fetch("/turnos/eliminar-turno/" + id, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert("Ocurrió un error al eliminar el turno.");
            }
        });
    }
}