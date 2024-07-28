# Aves de Merlo San Luis

Aves de Merlo San Luis es una aplicación móvil diseñada para ayudarte a identificar y registrar las aves que observas en la región de Merlo, San Luis. La aplicación permite a los usuarios almacenar una lista de aves observadas, elegir distintos recorridos en los alrededores de Merlo y proporciona una interfaz fácil de usar para la navegación y el registro de observaciones.

## Introducción

Estas instrucciones te guiarán en la configuración del entorno necesario para ejecutar la aplicación localmente en tu máquina o cómo descargarla desde Google Play Store.

### Prerrequisitos

Para probar la aplicación localmente, se necesita:
- Android Studio instalado en tu computadora.
- Un emulador de Android configurado en Android Studio.

### Instalación

1. Clona el repositorio del proyecto en tu máquina local.
git clone https://github.com/tu_usuario/aves-de-merlo-san-luis.git
Abre Android Studio y selecciona "Open an existing Android Studio project". Navega hasta el directorio donde clonaste el repositorio y selecciónalo. Asegúrate de que el emulador de Android esté configurado y funcionando.
Haz clic en el botón "Run" (el icono de play) en Android Studio para compilar y ejecutar la aplicación en el emulador.

2. Descarga desde Google Play Store (En proceso de verificación)
Puedes descargar e instalar la aplicación directamente desde la Google Play Store. 

## Uso

La aplicación permite a los usuarios:

1. Navegar a través de diferentes secciones utilizando NavHost.
2. Registrar aves observadas y almacenarlas localmente utilizando Room.
3. Consultar una lista de aves observadas.
4. Observar los distintos recorridos de observación en los alrededores de Merlo San Luis.

## Arquitectura y Tecnologías Utilizadas

1. MVVM: Arquitectura utilizada para separar las preocupaciones y facilitar el mantenimiento.
2. Jetpack Compose: Utilizado para construir la UI de forma declarativa.
3. Coroutines: Para la gestión de tareas asincrónicas.
4. Room: Para el almacenamiento local de datos.
5. Patrón Singleton: Para gestionar el contador de aves observadas.
6. Archivos JSON: Para la gestión y carga de datos.
7. NavHost: Para la navegación dentro de la aplicación.
8. Mockk para los tests unitarios y de integración.
9. AndroidJUnit4 para los tests instrumentados.
10. Git y Github para el control de versiones.

## CRÉDITOS

### Autor

Ing. Agustin Pernas - Desarrollador de la aplicación - [Perfil de LinkedIn](https://www.linkedin.com/in/agustinbpernas/)

### Agradecimientos

Raúl Balla, escritor del libro que me inspiró a desarrollar esta aplicación.

### Licencia

Este proyecto está licenciado bajo la Licencia MIT - mira el archivo [LICENSE.md](LICENSE.md) para más detalles.
