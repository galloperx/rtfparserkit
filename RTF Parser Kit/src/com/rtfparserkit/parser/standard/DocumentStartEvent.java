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

package com.rtfparserkit.parser.standard;

import com.rtfparserkit.parser.IRtfListener;

/**
 * Event represents the start of a document.
 */
class DocumentStartEvent implements IParserEvent
{
   /**
    * Pass the event to the listener.
    */
   public void fire(IRtfListener listener)
   {
      listener.processDocumentStart();
   }

   /**
    * Retrieve the event type.
    */
   public ParserEventType getType()
   {
      return ParserEventType.DOCUMENT_START_EVENT;
   }
}
