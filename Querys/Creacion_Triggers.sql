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
	, @fechaRegistro, @horaRegistro, 'Usuarios', 'Se ingresa al usuario ' + inserted.Nombre_Usuario +' a la tabla usuarios y es un: ' + (select Nombre_Rol from Roles where Id_Rol = inserted.Id_Rol)
	from inserted
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
			select 'El usuario se ingres� a la tabla Directores'
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
						select 'El usuario se ingres� a la tabla Administradores'
				end
					else 
					begin
						select 'El usuario se ingres� a la tabla usuario'
					end
			end
	end
	else 
	begin
		print 'Rol ingresado no existe'
	end
			
go

--Trigger para insertar los registros de cambios de proyectos en la bitacora correspondiente
create trigger TriggerInsercionBitacoraAvanceProyecto
on Proyectos
for insert
as
	declare @horaRegistro time(0) = getdate();
	declare @fechaRegistro date = CAST(GETDATE() as date);
	insert into BitacoraAvanceProyectos(Id_Proyecto, Fecha_Registro_Avance, Hora_Registro_Avance, Porcentaje_Avance, Estado_Avance, Observacion)
		select inserted.Id_Proyectos, @fechaRegistro, @horaRegistro, inserted.Porcentaje_Avance, inserted.Estado_Proyecto, inserted.Observacion
		from inserted
go

