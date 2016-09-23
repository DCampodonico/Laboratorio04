/*
 * Copyright (c) 2016 Daniel Campodonico; Emiliano Gioria; Lucas Moretti.
 * This file is part of Laboratorio04.
 *
 * Laboratorio04 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Laboratorio04 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Laboratorio04.  If not, see <http://www.gnu.org/licenses/>.
 */

package dam.isi.frsf.utn.edu.ar.laboratorio04.utils;

import java.io.Serializable;

import dam.isi.frsf.utn.edu.ar.laboratorio04.modelo.Ciudad;

/**
 * Created by martdominguez on 22/09/2016.
 */
public class FormBusqueda implements Serializable {
    private Double precioMinimo;
    private Double precioMaximo;
    private Ciudad ciudad;
    private Boolean permiteFumar;
    private Integer huespedes;

    public FormBusqueda(){}

    public FormBusqueda(Double precioMinimo, Double precioMaximo) {
        this(precioMinimo,precioMaximo,null,null,null);
    }

    public FormBusqueda(Ciudad ciudad) {
        this(null,null,ciudad,null,null);
    }

    public FormBusqueda(Double precioMinimo, Double precioMaximo, Ciudad ciudad, Boolean permiteFumar, Integer huespedes) {
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.ciudad = ciudad;
        this.permiteFumar = permiteFumar;
        this.huespedes = huespedes;
    }

    public Double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(Double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public Double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(Double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Boolean getPermiteFumar() {
        return permiteFumar;
    }

    public void setPermiteFumar(Boolean permiteFumar) {
        this.permiteFumar = permiteFumar;
    }

    public Integer getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(Integer huespedes) {
        this.huespedes = huespedes;
    }
}
