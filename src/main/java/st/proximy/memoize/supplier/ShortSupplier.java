//
// memoize - Memoize the return values of an expensive, dynamic state method.
// Copyright (C) 2021 Mariell Hoversholm
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
//

package st.proximy.memoize.supplier;

import java.util.function.Supplier;

/**
 * Represents a supplier of {@code short}-valued results. This is the {@code short}-producing primitive specialization
 * of {@link Supplier}.
 *
 * <p>
 * There is no requirement that a new or distinct result be returned each time the supplier is invoked.
 * </p>
 *
 * <p>
 * This is a {@link FunctionalInterface} whose functional method is {@link #getAsShort()}.
 * </p>
 *
 * @see Supplier
 */
@FunctionalInterface
public interface ShortSupplier {
  /**
   * Gets a result.
   *
   * @return a result.
   */
  short getAsShort();
}
