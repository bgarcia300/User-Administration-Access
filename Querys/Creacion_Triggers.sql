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
	insert into BitacoraControlUsuarios(Id_Usuario_Alterado, Nombre_Usuario_Alterado, Fecha_Registro_Modificacion, Hora_Registro_Modificacion
										, Tabla_Rol_Alterada, Observacion)
	select inserted.Id_Usuario, inserted.Nombre_Usuario +' '+inserted.Primer_Apellido_Usuario +' '+ inserted.Segundo_Apellido_Usuario
	, @fechaRegistro, @horaRegistro, 'Usuarios', 'Se ingresa al usuario ' + inserted.Nombre_Usuario +' a la tabla usuarios'
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
		insert into BitacoraControlUsuarios(Id_Usuario_Alterado, Nombre_Usuario_Alterado, Fecha_Registro_Modificacion, Hora_Registro_Modificacion
										, Tabla_Rol_Alterada, Observacion)
		select deleted.Id_Usuario, deleted.Nombre_Usuario +' '+deleted.Primer_Apellido_Usuario +' '+ deleted.Segundo_Apellido_Usuario
		, @fechaRegistro, @horaRegistro, 'Usuarios', 'Se elimina al usuario ' + deleted.Nombre_Usuario +' de  la tabla usuarios'
		from deleted
go



--Trigger Inserta Usuarios en tabla Administradores o Directores, segun rol ingresado
create trigger TriggerInsercionUsuarioEnTablaSegunRol
on Usuarios
for insert 
	as

	declare @rolIngresado nvarchar(15) = (select Nombre_Rol from Roles where Id_Rol = (select Id_Rol from inserted))
	
	if (@rolIngresado = 'Director' or @rolIngresado = 'Administrador' or @rolIngresado = 'Usuario')
	begin

		if (@rolIngresado = 'Director')
		begin
			insert into Directores(Id_Rol, Id_Usuario, Nombre_Director, Primer_Apellido_Director, Segundo_Apellido_Director
									, Telefono, Correo_Electronico, observaciones)
			select inserted.Id_Rol, inserted.Id_Usuario, inserted.Nombre_Usuario, inserted.Primer_Apellido_Usuario, inserted.Segundo_Apellido_Usuario
					, inserted.Telefono, inserted.Correo_Electronico, inserted.observaciones
			 from inserted
			select 'El usuario se ingresó a la tabla Directores'
		end

			else
			begin
				if (@rolIngresado = 'Administrador')
				begin
						insert into Administradores(Id_Rol, Id_Usuario, Nombre_Administrador, Primer_Apellido_Administrador, Segundo_Apellido_Administrador
									, Telefono, Correo_Electronico, observaciones)
						select inserted.Id_Rol, inserted.Id_Usuario, inserted.Nombre_Usuario, inserted.Primer_Apellido_Usuario, inserted.Segundo_Apellido_Usuario
					, inserted.Telefono, inserted.Correo_Electronico, inserted.observaciones
						 from inserted
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

select * from INFORMATION_SCHEMA.TABLES