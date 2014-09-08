
package Util;

import java.awt.Rectangle;

/**
 *
 * @author Sneyder G
 */
public class BoundBox extends Rectangle{
    
    private static final long serialVersionUID = 1L;

	public BoundBox( int x, int y, int w, int h ) {

		super( x, y, w, h );
	}

	public BoundBox( final BoundBox bbox ) {

		this( bbox.x, bbox.y, bbox.width, bbox.height );
	}

	public BoundBox normalize() {
		
		if ( x > x + width ) {
			
			x = x + width;
			width = -width;
		}
		
		if ( y > y + height ) {
			
			y = y + height;
			height = -height;
		}
		
		return this;
	}

	public BoundBox normalized() {
		
		return new BoundBox( this ).normalize();
	}
    
}
