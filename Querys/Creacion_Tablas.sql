use ProyectoFinal
go

--create table Roles
--(
--Id_Rol int primary key
--, Nombre_Rol nvarchar(15) unique not null
--, Funcion nvarchar(500)
--)
--go

--create table Usuarios
--(
--Id_Usuario int IDENTITY PRIMARY KEY
--, Nombre_Completo_Usuario nvarchar(30) unique not null
--, Telefono int
--, Funcion nvarchar(40)
--, Fecha_Nacimiento date not null
--, Id_Rol int NOT NULL
--, constraint FK_ROLES_USUARIOS foreign key (Id_Rol)
--references Roles (Id_Rol)
--)
--go


create table Administradores
(
Id_Administrador int IDENTITY primary key
, Id_Rol int NOT NULL
, Id_Usuario int NOT NULL
, Nombre_Completo_Administrador nvarchar(30) NOT NULL
, Observaciones nvarchar(500)
, constraint FK_ROLES_ADMINISTRADORES foreign key (Id_Rol)
references Roles (Id_Rol)
, constraint FK_USUARIOS_ADMINISTRADORES foreign key (Id_Usuario)
references Usuarios (Id_Usuario)
)
go

