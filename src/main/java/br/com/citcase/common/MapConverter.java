/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 Diego Rani Mazine
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package br.com.citcase.common;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.citcase.model.Edge;



public final class MapConverter {

	/** Lines delimiters. */
	private static final String LINE_DELIMITERS = "\r\n";

	/** Leg pattern. */
	private static final Pattern LEG_PATTERN = Pattern
			.compile("\\s*(\\w+)\\s*(\\w+)\\s*(\\d+)\\s*");

	
	/**
	 * Parse input map 
	 * 
	 * @param edge
	 * @return List<Edge>
	 * @throws IllegalArgumentException
	 */
	public static List<Edge> parseEdge(String edge) throws IllegalArgumentException {
		if (edge == null || edge=="") {
			throw new IllegalArgumentException("Erro ao criar Mapa. Valor Inválido");
		}
		
		// Lines tokenizer
		final StringTokenizer linesTokenizer = new StringTokenizer(edge,
				LINE_DELIMITERS);

		// The legs list
		final List<Edge> legs = new LinkedList<Edge>();

		// Iterates over each line
		for (int lineNumber = 1; linesTokenizer.hasMoreTokens(); lineNumber++) {
			// Returns the next token in this string tokenizer's string
			final String line = linesTokenizer.nextToken();

			// Gets a matcher that will match the input against the pattern
			final Matcher matcher = LEG_PATTERN.matcher(line);

			// Checks if the line formatted correctly
			if (!matcher.matches()) {
				throw new IllegalArgumentException(String.format(
						"Formato Inválido, erro na linha %d", lineNumber));
			}

			// Parses the current line
			legs.add(new Edge(matcher.group(1), matcher.group(2), Double
					.valueOf(matcher.group(3))));
		}

		return legs;
	}

}
