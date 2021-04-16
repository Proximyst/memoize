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

import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;
import st.proximy.memoize.supplier.ShortSupplier;

/**
 * A simple {@link Memoizable} for a {@code short}.
 */
public final class MemoizableShort implements Memoizable<@NonNull Short> {
  private final @NonNull ShortSupplier shortSupplier;
  private boolean hasMemoized = false;
  private short memoizedValue;

  public MemoizableShort(final @NonNull ShortSupplier shortSupplier) {
    this.shortSupplier = shortSupplier;
  }

  @SuppressWarnings("java:S4276")
  public MemoizableShort(final @NonNull Supplier<@NonNull Short> shortSupplier) {
    this.shortSupplier = shortSupplier::get;
  }

  public MemoizableShort(final short memoizedValue) {
    this.shortSupplier = () -> memoizedValue;
    this.hasMemoized = true;
    this.memoizedValue = memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #evaluateDataAsShort()
   */
  @Override
  public @NonNull Short evaluateData() {
    return this.evaluateDataAsShort();
  }

  /**
   * Evaluates the data like {@link #evaluateData()}, except with a primitive. See that method for more detail.
   *
   * @return the now memoized value.
   * @see #evaluateData()
   */
  public short evaluateDataAsShort() {
    this.hasMemoized = true;
    this.memoizedValue = this.shortSupplier.getAsShort();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #memoizedAsShort()
   */
  @Override
  public @NonNull Short memoized() {
    return this.memoizedAsShort();
  }

  /**
   * Returns the memoized value, or evaluates one then returns it if there is none currently memoized, just like in
   * {@link #memoized()} except with a primitive. See {@link #memoized()} for more detail.
   *
   * @return the now memoized value.
   * @see #memoized()
   */
  public short memoizedAsShort() {
    if (!this.hasMemoized) {
      return this.evaluateData();
    }

    return this.memoizedValue;
  }

  /**
   * Get whether there is currently a memoized value within this.
   *
   * @return whether there is a memoized value.
   */
  public boolean hasMemoized() {
    return this.hasMemoized;
  }
}
