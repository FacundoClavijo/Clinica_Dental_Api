function eliminarOdontologo(id) {
    if (confirm("¿Está seguro de que desea eliminar este odontologo?")) {
        fetch("/odontologos/eliminar-odontologo/" + id, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert("Ocurrió un error al eliminar el odontologo.");
            }
        });
    }
}