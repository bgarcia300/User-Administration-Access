--se crean las tablas con sus respectivas FK
use ProyectoFinal
go

create table Roles
(
Id_Rol int PRIMARY KEY
, Nombre_Rol nvarchar(15) unique not null
, Funcion nvarchar(500)
)
go

create table Usuarios
(
Id_Usuario int IDENTITY(1,1) PRIMARY KEY
, Id_Rol int NOT NULL
, Nombre_Usuario nvarchar(15) unique not null
, Primer_Apellido_Usuario nvarchar(15) unique not null
, Segundo_Apellido_Usuario nvarchar(15) unique not null
, Nombre_Login nvarchar(30) not null
, Contrasena binary(50) not null
, Funcion nvarchar(40)
, Fecha_Nacimiento date not null
, Edad AS (datediff(Day,Fecha_Nacimiento,getdate()))/(365)
, Telefono int not null
, Correo_Electronico nvarchar(40) not null
, Residencia nvarchar(500) not null
, observaciones nvarchar(500) null
, constraint FK_ROLES_USUARIOS foreign key (Id_Rol)
references Roles (Id_Rol)
)
go

create table Administradores
(
Id_Administrador int IDENTITY(1,1) PRIMARY KEY
, Id_Rol int NOT NULL
, Id_Usuario int NOT NULL
, Nombre_Administrador nvarchar(15) unique not null
, Primer_Apellido_Administrador nvarchar(15) unique not null
, Segundo_Apellido_Administrador nvarchar(15) unique not null
, Telefono int not null
, Correo_Electronico nvarchar(40) not null
, observaciones nvarchar(500) null
, constraint FK_ROLES_ADMINISTRADORES foreign key (Id_Rol)
references Roles (Id_Rol)
, constraint FK_USUARIOS_ADMINISTRADORES foreign key (Id_Usuario)
references Usuarios (Id_Usuario)
)
go

create table Directores
(
Id_Director int IDENTITY(1,1) PRIMARY KEY
, Id_Rol int NOT NULL
, Id_Usuario int NOT NULL
, Nombre_Director nvarchar(15) unique not null
, Primer_Apellido_Director nvarchar(15) unique not null
, Segundo_Apellido_Director nvarchar(15) unique not null
, Telefono int not null
, Correo_Electronico nvarchar(40) not null
, observaciones nvarchar(500) null
, constraint FK_ROLES_DIRECTORES foreign key (Id_Rol)
references Roles (Id_Rol)
, constraint FK_USUARIOS_DIRECTORES foreign key (Id_Usuario)
references Usuarios (Id_Usuario)
)
go

create table Grupos
(
Id_Grupo int IDENTITY(1,1) PRIMARY KEY
, Id_Director int NOT NULL
, Id_Usuario int NOT NULL
, Nombre_Grupo nvarchar(40) NOT NULL
, Descripcion nvarchar(500)
, constraint FK_DIRECTORES_GRUPOS foreign key (Id_Director)
references Directores (Id_Director)
, constraint FK_USUARIOS_GRUPOS foreign key (Id_Usuario)
references Usuarios (Id_Usuario)
)
go

create table Proyectos
(
Id_Proyectos int IDENTITY(1,1) PRIMARY KEY
, Id_Grupo int NOT NULL
, Nombre_Proyecto nvarchar(40) NOT NULL
, Porcentaje_Avance int NOT NULL
, Estado_Proyecto AS ( 
CASE 
	WHEN Porcentaje_Avance >= 0 and Porcentaje_Avance <= 30 THEN 'Comenzando' 
	WHEN Porcentaje_Avance >= 31 and Porcentaje_Avance <= 75 THEN 'En Proceso' 
	WHEN Porcentaje_Avance >= 76 and Porcentaje_Avance <= 99 THEN 'Finalizando' 
	WHEN Porcentaje_Avance = 100 THEN 'Completado' 
	ELSE 'No se tiene conocimiento del estado'
END
)
, Observacion nvarchar(500)
, constraint FK_GRUPOS_PROYECTOS foreign key (Id_Grupo)
references GRUPOS (Id_Grupo)
)
go

create table BitacoraAvanceProyectos
(
Id_Bitacor_Avance_Proyecto int IDENTITY(1,1) PRIMARY KEY
, Id_Proyecto int NOT NULL
, Fecha_Registro_Avance date NOT NULL
, Hora_Registro_Avance time NOT NULL
, Porcentaje_Avance numeric(9,2) NOT NULL
, Estado_Avance varchar(50) NOT NULL
, Observacion varchar(500) NOT NULL
)
go

create table BitacoraControlUsuarios
(
Id_Bitacora_Usuario int IDENTITY(1,1) PRIMARY KEY
, Id_Usuario_Alterado int NOT NULL
, Nombre_Usuario_Alterado nvarchar(30) NOT NULL
, Fecha_Registro_Modificacion date NOT NULL
, Hora_Registro_Modificacion time(0) NOT NULL
, Tabla_Rol_Alterada varchar(50) NOT NULL
, Observacion varchar(500)
)