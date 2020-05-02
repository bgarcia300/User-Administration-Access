use ProyectoFinal
go

--Insercion de los roles
insert into roles values (1, 'Usuario', 'Solo puede trabajar en el proyecto')
go
insert into roles values (2, 'Administrador', 'Puede agregar usuario pero no editar bitácoras')
go
insert into roles values (3, 'Director', 'Puede editar bitácoras pero no agregar usuarios')
go

--Procedures
create procedure InsercionUsuario
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
				values(@Id_Rol, UPPER(@Nombre_Usuario), UPPER(@Primer_Apellido_Usuario), UPPER(@Segundo_Apellido_Usuario), UPPER(@Funcion), @Fecha_Nacimiento, @Telefono
						, LOWER(@Correo_Electronico), UPPER(@Residencia), LOWER(@Nombre_Login), @Contrasena, LOWER(@Nombre_Username), UPPER(@observaciones))
end
go

create procedure EliminacionUsuarioPorNombreCompleto
(
@nombreUsuario nvarchar(150)
)
as
begin
	declare @login nvarchar(30) = (select Nombre_login from Usuarios where Nombre_Usuario + ' ' + Primer_Apellido_Usuario + ' ' + Segundo_Apellido_Usuario = @nombreUsuario)
	declare @userName nvarchar(30) = (select Nombre_Username from Usuarios where Nombre_Usuario + ' ' + Primer_Apellido_Usuario + ' ' + Segundo_Apellido_Usuario = @nombreUsuario)	
	exec sp_droplogin @login
	exec sp_dropuser @userName
	delete from Usuarios where Nombre_Usuario + ' ' + Primer_Apellido_Usuario + ' ' + Segundo_Apellido_Usuario = @nombreUsuario
	delete from Administradores where Nombre_Administrador + ' ' + Primer_Apellido_Administrador + ' ' + Segundo_Apellido_Administrador = @nombreUsuario
	delete from Directores where Nombre_Director + ' ' + Primer_Apellido_Director + ' ' + Segundo_Apellido_Director = @nombreUsuario
end
go

create procedure InsercionNuevoProyecto
(
@idGrupo int
, @nombreProyecto nvarchar(40)
, @porcentajeAvance int
, @observacion nvarchar(500)
)
as
begin
	insert into Proyectos(Id_Grupo, Nombre_Proyecto, Porcentaje_Avance, Observacion)
	values(@idGrupo, UPPER(@nombreProyecto), @porcentajeAvance, UPPER(@observacion))
end
go

create procedure InsercionNuevoGrupo
(
@idDirector int
, @idUsuario nvarchar(max)
, @nombreGrupo nvarchar(40)
, @descripcion nvarchar(500)
)
as
begin
	insert into Grupos(Id_Director, Id_Usuario, Nombre_Grupo, Descripcion)
	values(@idDirector, UPPER(@idUsuario), UPPER(@nombreGrupo), UPPER(@descripcion))
end
go

create procedure ActualizacionPorcentajeAvanceProyecto
(
@nombreProyecto nvarchar(40)
, @porcentajeAvance int
, @observacion nvarchar(500)
)
as
begin
	update Proyectos
	set Porcentaje_Avance = UPPER(@porcentajeAvance), observacion = @observacion
	where Nombre_Proyecto = @nombreProyecto
end
go

create procedure ActualizacionUsuariosGrupos
(
@nombreGrupo nvarchar(40)
, @idUsuarios nvarchar(100)
, @descripcion nvarchar(500)
)
as
begin
	update Grupos
	set Id_Usuario = UPPER((select Id_Usuario from Grupos where Nombre_Grupo = @nombreGrupo) +','+@idUsuarios), Descripcion = UPPER(@descripcion)
	where Nombre_Grupo = @nombreGrupo
end
go

create procedure EliminacionGrupoProNombre
(
@nombreGrupo nvarchar(40)
, @descripcion nvarchar(500)
)
as
begin
	update Grupos
	set Descripcion = UPPER(@descripcion)
	where Nombre_Grupo = @nombreGrupo
	delete from grupos where Nombre_Grupo = @nombreGrupo
end
go

create procedure EliminacionProyectoPorNombre
(
@nombreProyecto nvarchar(40)
, @observacion nvarchar(500)
)
as
begin
	update Proyectos
	set Observacion = UPPER(@observacion)
	where Nombre_Proyecto = @nombreProyecto
	delete from Proyectos where Nombre_Proyecto = @nombreProyecto
end
go