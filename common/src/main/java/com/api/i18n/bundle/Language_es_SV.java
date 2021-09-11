package com.api.i18n.bundle;

import java.util.ListResourceBundle;

public class Language_es_SV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                // General
                {"register", "Registrarse"},
                {"login", "Acceso"},
                {"message","Mensaje"},
                {"nickname","Apodo"},
                {"password","Contraseña"},
                {"warning", "Una advertencia"},
                {"loginFailure","Nombre de usuario y/o contraseña inválido"},
                {"registrationFailure","Ya existe un usuario con el mismo nombre"},
                {"greeting_sever","El terminal de control del servidor lo recibe. Ingrese save para guardar la colección, exit para detener el servidor."},
                {"language", "Idioma"},
                {"help","Ayudar"},
                {"about","Acerca de"},
                {"mode","Modo"},
                {"table","Mesa"},
                {"canvas","Lienzo"},
                {"logOut","Cerrar sesión"},
                {"desk","Escritorio"},
                {"command","Mando"},
                {"action","Actuar"},
                {"general","General"},
                {"submit","Confirmar"},
                {"clear","Claro"},
                {"info","Info"},
                {"coordinate_view","Representación coordinada"},
                {"enums","Enumeraciones"},
                {"submit_warning", "Error de ejecución del comando"},


// Dragon info
                {"dragon","El dragón"},
                {"dragons","Dragones"},
                {"id","Identificador"},
                {"name","Nombre"},
                {"color","Color"},
                {"coordinates","Coordenadas"},
                {"creationDate","Fecha de creación"},
                {"age","Edad"},
                {"type","Un tipo"},
                {"size","El tamaño"},
                {"character","Personaje"},
                {"cave","Cueva"},
                {"depth","Profundidad"},
                {"numberOfTreasure","Cantidad de riqueza"},
                {"location","Localización"},
                {"xCoordinate","X coordinar"},
                {"yCoordinate","Y coordinar"},

// Command description
                {"infoAdd","Agregar nuevo artículo a la colección"},
                {"infoClear","Colección clara"},
                {"infoExecuteScript","Leer y ejecutar el script desde el archivo"},
                {"infoExit","Finalizar el programa (sin guardar en archivo)"},
                {"infoFilterGreaterThanColor","Mostrar elementos cuyo valor de campo color es mayor que el especificado"},
                {"infoFilterLessThanCharacter","Mostrar elementos cuyo valor de campo character sea menor que el especificado"},
                {"infoHelp","Mostrar ayuda para los comandos disponibles"},
                {"infoInfo","Mostrar información sobre una colección"},
                {"infoInsertAtIndex","Agregar un artículo en una posición determinada"},
                {"infoRemoveAllByCave","Quitar elemento cuyo valor cave sea igual al dado"},
                {"infoRemoveAtIndex","Eliminar elemento en una posición determinada"},
                {"infoRemoveById","Eliminar elemento cuyo ID es igual al dado"},
                {"infoSave","Guardar colección en archivo"},
                {"infoShow","Colección de exhibición"},
                {"infoShuffle","Mezclar los elementos de la colección en orden aleatorio"},
                {"infoUpdateId","Actualiza el valor del elemento con el ID dado."},

// Info
                {"askForNull","Ingrese NULL para omitir las características de la cueva (o cualquier otro valor para continuar):"},

// Exceptions
                {"scriptNotValid","Secuencia de comandos no válida"},
                {"fileNotFound","Archivo no encontrado"},
                {"invalidInput","Entrada inválida"},
                {"inputGreaterZero","El valor del campo debe ser mayor que cero!"},
                {"notNullField","El campo no puede ser null"},
                {"noSuchCommand","Equipo desconocido"},
                {"recursiveCallScript","No puede llamar a un archivo desde sí mismo!"},

// Obj input
                {"enterName","Introduzca su nombre:"},
                {"enterCoordinates","Introduzca las coordenadas (doble, int separadas por un espacio):"},
                {"enterAge","Ingrese la edad:"},
                {"enterColor","Ingrese color (RED, BLACK, BLUE, ORANGE):"},
                {"enterType","Ingrese el tipo (WATER, AIR, FIRE):"},
                {"enterCharacter","Ingrese carácter (CUNNING, CHAOTIC, FICKLE):"},
                {"enterDepth","Entra en la profundidad de la cueva:"},
                {"enterWealth","Ingrese la cantidad de riqueza:"},

// Bool
                {"booleanOpTrue","La operación se realizó con éxito"},
                {"booleanOpFalse","Operación fallida"}
        };
    }
}
