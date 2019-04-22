package com.kys.poi.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Poi style Builder
 *
 * @author kys0213
 * @since  2018. 4. 30.
 */
@FunctionalInterface
public interface CellStyleBuilder {

	public CellStyle build(Workbook workBook);
}
