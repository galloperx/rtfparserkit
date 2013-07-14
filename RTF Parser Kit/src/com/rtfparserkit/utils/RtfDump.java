/*
 * Copyright 2013 Jon Iles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rtfparserkit.utils;

import java.io.OutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.rtfparserkit.parser.IRtfListener;
import com.rtfparserkit.rtf.Command;
import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

/**
 * Trivial class used to convert events generated by an RTF parser into an XML document.
 * The primary purpose of this code is to debug the parser output, and provide a
 * convenient method for comparing expected and actaul parser behaviour in test cases.
 * 
 * Note that we're using an internal Sun class to produce indented XML. Not strictly
 * necessary, but it makes the output more readable.
 */
@SuppressWarnings("restriction")
public class RtfDump implements IRtfListener
{
   /**
    * Constructor.
    */
   public RtfDump(OutputStream stream)
      throws XMLStreamException
   {
      writer = new IndentingXMLStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(stream, "UTF-8"));
   }

   /**
    * Create the document header.
    */
   @Override
   public void processDocumentStart()
   {
      try
      {
         writer.writeStartDocument("UTF-8", "1.0");
         writer.writeStartElement("rtf");
      }
      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Create the document trailer.
    */
   @Override
   public void processDocumentEnd()
   {
      try
      {
         writer.writeEndElement();
         writer.writeEndDocument();
      }
      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Write character bytes - note that we cheat, we just convert them
    * directly to a string for output with no regard to the encoding.
    */
   @Override
   public void processCharacterBytes(byte[] data)
   {
      try
      {
         if (data.length != 0)
         {
            writer.writeStartElement("chars");
            writer.writeCharacters(new String(data));
            writer.writeEndElement();
         }
      }

      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Write binary data as hex.
    */
   @Override
   public void processBinaryBytes(byte[] data)
   {
      try
      {
         writer.writeStartElement("bytes");
         for (byte b : data)
         {
            writer.writeCharacters(Integer.toHexString(b));
         }
         writer.writeEndElement();
      }

      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Write a group start tag.
    */
   @Override
   public void processGroupStart()
   {
      try
      {
         writer.writeStartElement("group");
      }

      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Write a group end tag.
    */
   @Override
   public void processGroupEnd()
   {
      try
      {
         writer.writeEndElement();
      }

      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Write a command tag.
    */
   @Override
   public void processCommand(Command command, int parameter, boolean hasParameter, boolean optional)
   {
      try
      {
         writer.writeEmptyElement("command");
         writer.writeAttribute("name", command.getCommandName());

         if (hasParameter)
         {
            writer.writeAttribute("parameter", Integer.toString(parameter));
         }

         if (optional)
         {
            writer.writeAttribute("optional", "true");
         }
      }
      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Write string data.
    */
   @Override
   public void processString(String string)
   {
      try
      {
         writer.writeStartElement("chars");
         writer.writeCharacters(string);
         writer.writeEndElement();
      }
      catch (XMLStreamException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   private final XMLStreamWriter writer;
}
