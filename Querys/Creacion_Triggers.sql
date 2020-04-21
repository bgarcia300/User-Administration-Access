--Creacion de Triggers
use ProyectoFinal
go

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



--Trigger Inserta Usuarios en tabla Administradores o Directores, segun rol ingresado
alter trigger TriggerInsercionUsuarioEnTablaSegunRol
on Usuarios
for insert 
	as

	declare @rolIngresado nvarchar(15) = (select Nombre_Rol from Roles where Id_Rol = (select Id_Rol from inserted))
	
	if (@rolIngresado = 'Director' or @rolIngresado = 'Administrador' or @rolIngresado = 'Usuario')
	begin

		if (@rolIngresado = 'Director')
		begin
			insert into Directores(Id_Rol, Id_Usuario, Nombre_Completo_Director)
			select Id_Rol, Id_Usuario, Nombre_Completo_Usuario from inserted
			select 'El usuario se ingresó a la tabla Directores'
		end

			else
			begin
				if (@rolIngresado = 'Administrador')
				begin
						insert into Administradores(Id_Rol, Id_Usuario, Nombre_Completo_Administrador) 
						select Id_Rol, Id_Usuario, Nombre_Completo_Usuario from inserted
						select 'El usuario se ingresó a la tabla Administradores'
				end
					else 
					begin
						select 'El usuario se ingresó a la tabla usuario'
					end
			end
	end
	else 
	begin
		print 'Rol ingresado no existe'
	end
			
go




	--declare @rolIngresado nvarchar(15) = (select Nombre_Rol from Roles where Roles.Id_Rol = inserted.id_rol)
	--if	(@rolIngresado = 'Director' or @rolIngresado = 'Administrador' or @rolIngresado = 'Usuario')
	--				if	(@rolIngresado = 'Director')
							
						


select * from roles
select * from Administradores
select * from Usuarios
select * from Directores
select * from BitacoraControlUsuarios

insert into Roles values(3, 'Director', 'Puede ingresar datos del proyecto en bitacora')
go
insert Usuarios(Nombre_Completo_Usuario, Telefono, Funcion, Fecha_Nacimiento, Id_Rol)
values('Marco User', 83768060, 'Trabaja en proyecto', '1985-05-16', 1)

delete from Usuarios where Id_Usuario = 12

select * from usuarios
select * from BitacoraControlUsuarios
