use ProyectoFinal
go

--Insercion de los roles
insert into roles values (1, 'Usuario', 'Solo puede trabajar en el proyecto')
go
insert into roles values (2, 'Administrador', 'Puede agregar usuario pero no editar bitácoras')
go
insert into roles values (3, 'Director', 'Puede editar bitácoras pero no agregar usuarios')
go

alter procedure InsercionUsuario
(
@Id_Rol int
, @Nombre_Usuario nvarchar(15)
, @Primer_Apellido_Usuario nvarchar(15)
, @Segundo_Apellido_Usuario nvarchar(15)
, @Funcion nvarchar(40)
, @Fecha_Nacimiento date
, @Telefono int
, @Correo_Electronico nvarchar(40)
, @Residencia nvarchar(500)
, @Nombre_Login nvarchar(30)
, @Contrasena nvarchar(40)
, @Nombre_Username nvarchar(30)
, @observaciones nvarchar(500)
)
as
begin
	insert into Usuarios(Id_rol, Nombre_Usuario, Primer_Apellido_Usuario, Segundo_Apellido_Usuario, Funcion, Fecha_Nacimiento, Telefono
						, Correo_Electronico, Residencia, Nombre_Login, Contrasena, Nombre_Username, Observaciones)
				values(@Id_Rol, LOWER(@Nombre_Usuario), LOWER(@Primer_Apellido_Usuario), LOWER(@Segundo_Apellido_Usuario), LOWER(@Funcion), @Fecha_Nacimiento, @Telefono
						, LOWER(@Correo_Electronico), LOWER(@Residencia), LOWER(@Nombre_Login), @Contrasena, LOWER(@Nombre_Username), LOWER(@observaciones))
end
go

