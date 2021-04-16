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

import java.util.function.IntSupplier;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Memoizable} for a {@code int}.
 */
public final class MemoizableInteger implements Memoizable<@NonNull Integer> {
  private final @NonNull IntSupplier intSupplier;
  private boolean hasMemoized = false;
  private int memoizedValue;

  public MemoizableInteger(final @NonNull IntSupplier intSupplier) {
    this.intSupplier = intSupplier;
  }

  @SuppressWarnings("java:S4276")
  public MemoizableInteger(final @NonNull Supplier<@NonNull Integer> intSupplier) {
    this.intSupplier = intSupplier::get;
  }

  public MemoizableInteger(final int memoizedValue) {
    this.intSupplier = () -> memoizedValue;
    this.hasMemoized = true;
    this.memoizedValue = memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #evaluateDataAsInteger()
   */
  @Override
  public @NonNull Integer evaluateData() {
    return this.evaluateDataAsInteger();
  }

  /**
   * Evaluates the data like {@link #evaluateData()}, except with a primitive. See that method for more detail.
   *
   * @return the now memoized value.
   * @see #evaluateData()
   */
  public int evaluateDataAsInteger() {
    this.hasMemoized = true;
    this.memoizedValue = this.intSupplier.getAsInt();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #memoizedAsInteger()
   */
  @Override
  public @NonNull Integer memoized() {
    return this.memoizedAsInteger();
  }

  /**
   * Returns the memoized value, or evaluates one then returns it if there is none currently memoized, just like in
   * {@link #memoized()} except with a primitive. See {@link #memoized()} for more detail.
   *
   * @return the now memoized value.
   * @see #memoized()
   */
  public int memoizedAsInteger() {
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
