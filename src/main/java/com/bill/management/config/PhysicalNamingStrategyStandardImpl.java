package com.bill.management.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PhysicalNamingStrategyStandardImpl implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		// Acme naming standards do not apply to catalog names
		return name;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		// Acme naming standards do not apply to schema names
		return name;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		final List<String> parts = splitAndReplace( name.getText() );
		return jdbcEnvironment.getIdentifierHelper().toIdentifier(
				join( parts ),
				name.isQuoted()
		);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		final LinkedList<String> parts = splitAndReplace( name.getText() );
		// Acme Corp says all sequences should end with _seq
		if ( !"seq".equalsIgnoreCase( parts.getLast() ) ) {
			parts.add( "seq" );
		}
		return jdbcEnvironment.getIdentifierHelper().toIdentifier(
				join( parts ),
				name.isQuoted()
		);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		final List<String> parts = splitAndReplace( name.getText() );
		return jdbcEnvironment.getIdentifierHelper().toIdentifier(
				join( parts ),
				name.isQuoted()
		);
	}
	
	private LinkedList<String> splitAndReplace(String name) {
		LinkedList<String> result = new LinkedList<>();
		for ( String part : StringUtils.splitByCharacterTypeCamelCase( name ) ) {
			if ( part == null || part.trim().isEmpty() ) {
				// skip null and space
				continue;
			}
			result.add( part.toLowerCase( Locale.ROOT ) );
		}
		return result;
	}

	private String join(List<String> parts) {
		boolean firstPass = true;
		String separator = "";
		StringBuilder joined = new StringBuilder();
		for ( String part : parts ) {
			joined.append( separator ).append( part );
			if ( firstPass ) {
				firstPass = false;
				separator = "_";
			}
		}
		return joined.toString().toUpperCase();
	}
}
