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

package st.proximy.memoize;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;

/**
 * Memoize a value with a method.
 *
 * @param <T> the type to be memoized.
 */
public interface Memoizable<T> {
  /**
   * Evaluate the inner value of the memoizable value.
   * <p>
   * <i>Note:</i> This does not guarantee this will evaluate anything after <i>first</i> evaluation. <br>
   * <i>Note:</i> You should probably use {@link #memoized()} to get the memoized value, or evaluate the value if not
   * yet memoized.
   * </p>
   *
   * @return the now memoized value.
   * @see #memoized()
   * @see #eager()
   */
  T evaluateData();

  /**
   * Get the memoized value, or evaluate and memoize the result of {@link #evaluateData()}.
   *
   * @return the now memoized value.
   */
  T memoized();

  /**
   * Eagerly evaluate the value of this {@link Memoizable}. This will ensure there is an evaluated value.
   *
   * @return the very same memoizable with data evaluated.
   */
  default @NonNull @This Memoizable<T> eager() {
    this.evaluateData();
    return this;
  }
}
