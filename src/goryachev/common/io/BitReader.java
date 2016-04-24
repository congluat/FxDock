// Copyright (c) 2013-2016 Andy Goryachev <andy@goryachev.com>
package goryachev.common.io;
import java.io.ByteArrayInputStream;


public class BitReader
	extends BitStreamReader
{
	public BitReader(byte[] bytes)
	{
		super(new ByteArrayInputStream(bytes));
	}
}
