/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.models;

/**
 * Values for the status of a task
 * Eventually Users will be able to define their 
 * own values for taskStatus.
 */
public enum TaskStatus {
	NEW,
	SCHEDULED, 
	IN_PROGRESS,
	COMPLETE 
}