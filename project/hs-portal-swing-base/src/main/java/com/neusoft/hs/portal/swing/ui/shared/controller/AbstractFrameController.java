package com.neusoft.hs.portal.swing.ui.shared.controller;

import com.neusoft.hs.platform.exception.HsException;

public abstract class AbstractFrameController extends AbstractController {

	public abstract void prepareAndOpenFrame() throws HsException;

}
