package com.eerichmond.core.data;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class JpaSchemaExporter {

	private static final Logger LOG = LoggerFactory.getLogger(JpaSchemaExporter.class);

	/**
	 * @param args	1. The destination
	 *              2. Persistence unit name
	 *              3+. Hibernate settings name/value pairs in the format of name=value
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();

		for (int i = 2; i < args.length; i++) {
			String[] setting = args[i].split("=");
			properties.setProperty(setting[0], setting[1]);
		}

		execute(args[0], args[1], properties);
	}

	@SuppressWarnings("deprecation")
	public static void execute(String destination, String persistenceUnitName, Properties properties) {
		LOG.debug("Starting schema export");

		Ejb3Configuration jpaCfg = new Ejb3Configuration().configure(persistenceUnitName, properties);

		Configuration hibernateCfg = jpaCfg.getHibernateConfiguration();

		SchemaExport schemaExport = new SchemaExport(hibernateCfg);
		schemaExport.setOutputFile(destination);
		schemaExport.setFormat(true);
		schemaExport.execute(Target.SCRIPT, SchemaExport.Type.BOTH);

		LOG.debug("Schema exported to " + destination);
	}
}

