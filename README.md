# 🌟 Reserve Now: Reserva de la forma más rápida

**Trabajo Final de Grado – Desarrollo de Aplicaciones Multiplataforma (DAM)**
**Alumno:** Miguel Angel Virgil Abad

---

## 📌 Índice
- [📖 Introducción](#introduccion)
- [⚙️ Funcionalidades y Tecnologías](#funcionalidades-y-tecnologias)
- [🛠️ Guía de Instalación](#guia-de-instalacion)
- [🚀 Guía de Uso](#guia-de-uso)
- [🧾 Conclusión](#conclusion)

---

## 📖 Introducción
**Reserve Now** es una aplicación Android diseñada para ayudarte a reservar mesa en restaurantes de la forma más fácil, intuitiva y rápida.

### 🎯 Objetivos
- Crear una aplicación funcional y amigable para usuarios con diferentes niveles tecnológicos.
- Aplicar los conocimientos adquiridos a lo largo del ciclo formativo DAM.
- Desarrollar una interfaz clara, accesible y moderna.

### 💡 Motivación
En un mundo donde cada minuto cuenta y las experiencias gastronómicas son cada vez más valoradas, surge la necesidad de una herramienta que facilite la reserva en restaurantes de forma rápida, cómoda y eficiente. Este proyecto responde a esa necesidad, permitiendo a los usuarios organizar sus planes culinarios sin complicaciones, y a los restaurantes optimizar su gestión de mesas.

---

## ⚙️ Funcionalidades y Tecnologías

### 🧩 Funcionalidades
- ✅ Menú de navegación atractivo e intuitivo
- ✅ Sistema de reservas con todo tipo de comprobaciones capturadas para ayudar lo máximo posible al usuario
- ✅ Gestión de Reservas, mesas, restaurantes desde la propia BDD.
- ✅ Integración con Google Maps para geolocalización y direcciones
- ✅ Menú de reseñas en el que puedas publicar tu opinión sobre los restaurantes

### 🛠️ Tecnologías utilizadas
- **Android Studio con Java** para la app Android
- **Java SpringBoot** para el microservicio
- **Base de datos MySQL**

---

## 🛠️ Guía de Instalación
1. `git clone https://github.com/Miguelitooz/ReserveNow.git`
2. Abrir el **microservicio** en tu entorno favorito (en mi caso, Visual Studio Code).
3. Abrir la **aplicación** en Android Studio y descargar un dispositivo virtual en la app o conectar un móvil Android al PC.

---

## 🚀 Guía de Uso

### 1. Menú
#### Barra superior:
- A la izquierda hay un botón (icono) que abre la sección de **Reseñas**.
- En el centro se muestra el logo de la app (“ReserveNow”).

#### Área central:
- Cuando el usuario no tiene reservas, aparece un aviso con el texto “No tienes reservas” y debajo un botón “Hacer Reserva”.
- Si hubiera reservas, aquí se muestra la reserva activa del usuario.

#### Barra de navegación inferior:
- **Ícono “Perfil” (usuario):** Cuando el usuario no tiene reservas, aparece un aviso con el texto “No tienes reservas” y debajo un botón “Hacer Reserva”. Si hubiera reserva, aquí se muestra la reserva activa del usuario.
- **Ícono “Reservas” (calendario):** Sección en la que puedes realizar reservas.
- **Ícono “Restaurantes” (cubiertos):** Listado de restaurantes disponibles donde puedes ver sus platos y precios.
- **Ícono “Ajustes” (engranaje):** Información de la cuenta, cerrar sesión y darte de baja.

### 2. Inicio de sesión / Autenticación
#### Login / Registro
- La primera vez, si el usuario no está autenticado, muestra la pantalla de registro y login (campos usuario, email y contraseña).
- Al hacer login correctamente, se guarda el **token JWT** en `SessionManager` y pasas al menú principal de la app.
- Mientras el token no esté caducado, el usuario permanece logueado; una vez caduque, te devolverá a la pantalla de login y tendrás que volver a iniciar sesión.

---

## 🧾 Conclusión

En conclusión, **Reserve Now** es una aplicación práctica y fácil de usar que simplifica el proceso de reservar mesa en restaurantes. Combina un backend robusto con Spring Boot y una app Android intuitiva, gestionando autenticación, reservas y reseñas de manera segura. Si bien no cuenta con un gran catálogo de funcionalidades, he preferido centrarme en el diseño de interfaz de la app, incluyendo las funciones básicas y los detalles necesarios para darle sentido a la aplicación. Para desarrollar este proyecto, he utilizado tanto los conocimientos adquiridos en la formación como videos informativos y distintas Inteligencias Artificiales. Todo esto me ha ayudado no solo a alcanzar mis objetivos personales relacionados con el trabajo final, sino también a mejorar como "Profesional" gracias al tiempo dedicado al proyecto.

---

