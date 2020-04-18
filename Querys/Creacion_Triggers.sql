--Creacion de Triggers
use ProyectoFinal
go
{
--Trigger registra en bitacora cuando se registra un usuario nuevo
create trigger TriggerInsercionBitacoraControlUsuario
on Usuarios
for insert
as
	declare @horaRegistro time(0) = getdate();
	declare @fechaRegistro date = CAST(GETDATE() as date)
	insert into BitacoraControlUsuarios
	select inserted.Id_Usuario, inserted.Nombre_Completo_Usuario, @fechaRegistro, @horaRegistro, 'Usuarios', 'Se ingresa al usuario ' + inserted.Nombre_Completo_Usuario +' a la tabla usuarios'
	from inserted
go

disable trigger TriggerInsercionBitacoraControlUsuario
go
--Trigger registra en bitacora cuando se elimina usuario
create trigger TriggerEliminacionBitacoraControlUsuario
on Usuarios
for delete
	as
		declare @horaRegistro time(0) = getdate();
		declare @fechaRegistro date = CAST(GETDATE() as date)
		insert into BitacoraControlUsuarios
		select deleted.Id_Usuario, deleted.Nombre_Completo_Usuario, @fechaRegistro, @horaRegistro, 
		'Usuarios', 'Se elimina al usuario ' + deleted.Nombre_Completo_Usuario +' de la tabla usuarios'
		from deleted
go

disable trigger TriggerEliminacionBitacoraControlUsuario
go
}

--Trigger Inserta Usuarios en tabla segun rol 
create trigger TriggerInsercionUsuarioEnDirectores
on Usuarios
for insert 
	as
		insert into Directores(Id_Rol, Id_Usuario, Nombre_Completo_Director) 
		values (inserted.Id_Rol, inserted.Id_Usuario, inserted.Nombre_Completo_Usuario)
go



	declare @rolIngresado nvarchar(15) = (select Nombre_Rol from Roles where Roles.Id_Rol = inserted.id_rol)
	if	(@rolIngresado = 'Director' or @rolIngresado = 'Administrador' or @rolIngresado = 'Usuario')
					if	(@rolIngresado = 'Director')
							
						else
					if (@rolIngresado = 'Administrador')
							insert into Administradores(Id_Rol, Id_Usuario, Nombre_Completo_Administrador) 
							values (@idRol, @idUsuarioIngresado, @nombreCompleto, @observaciones)
						else 
							print 'El usuario se ingresó a la tabla usuario'
	else 
		print 'Rol ingresado no existe'




insert into Roles values(1, 'Usuario', 'Puede trabajar en el proyecto')
go
insert Usuarios(Nombre_Completo_Usuario, Telefono, Funcion, Fecha_Nacimiento, Id_Rol)
values('Bayron Garcia Asy', 83164544, 'Dev', '2002-07-15', 1)

delete from Usuarios where Id_Usuario = 12

select * from usuarios
select * from BitacoraControlUsuarios
